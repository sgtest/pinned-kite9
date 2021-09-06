package com.kite9.server.persistence.github;

import static com.kite9.server.persistence.PathUtils.FILEPATH;
import static com.kite9.server.persistence.PathUtils.OWNER;
import static com.kite9.server.persistence.PathUtils.REPONAME;
import static com.kite9.server.persistence.PathUtils.getPathSegment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.kite9.pipeline.adl.format.media.K9MediaType;
import com.kite9.pipeline.uri.K9URI;
import com.kite9.server.adl.format.media.DiagramFileFormat;
import com.kite9.server.topic.ChangeBroadcaster;
import com.kite9.server.update.Update;
import org.kite9.diagram.common.Kite9XMLProcessingException;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kite9.pipeline.adl.format.FormatSupplier;
import com.kite9.pipeline.adl.format.media.Format;
import com.kite9.pipeline.adl.holder.meta.MetaReadWrite;
import com.kite9.pipeline.adl.holder.meta.UserMeta;
import com.kite9.server.persistence.PathUtils;
import com.kite9.server.persistence.RelativeHostLinkBuilder;
import com.kite9.server.persistence.cache.CacheManagedAPIFactory;
import com.kite9.server.sources.SourceAPI;

public final class GithubSourceAPIFactory extends CacheManagedAPIFactory implements InitializingBean {

	protected OAuth2AuthorizedClientRepository clientRepository;
	
	private FormatSupplier formatSupplier;
	
	private AbstractGithubEntityConverter ec;
	
	private ObjectMapper om;
	
	private ChangeBroadcaster broadcaster;
		
	public GithubSourceAPIFactory(OAuth2AuthorizedClientRepository clientRepository, FormatSupplier formatSupplier, ChangeBroadcaster broadcaster) {
		super();
		this.clientRepository = clientRepository;
		this.formatSupplier = formatSupplier;
		this.broadcaster = broadcaster;
	}

	public SourceAPI createBackingAPI(K9URI u, Authentication authentication) throws Exception {
		String path = u.getPath();
		if (GithubContentController.GITHUB.equals(PathUtils.getPathSegment(PathUtils.TYPE, path))) {
			String owner = getPathSegment(OWNER, path);
			String reponame = getPathSegment(REPONAME, path);
			String filepath = getPathSegment(FILEPATH, path);
			
			if (owner == null) {
				return new GithubDirectoryAPI(path,  ec.getHomePage(authentication));
			} else if (reponame == null) {
				return new GithubDirectoryAPI(path, ec.getOrgPage(owner, authentication));
			} else {
				try {
					Object o = AbstractGithubFileAPI.getGHContent(authentication, clientRepository, owner, reponame, filepath);
				
					if (o instanceof List) {
						// it's a directory page
						TypeReference<List<GHContent>> contentList = new TypeReference<List<GHContent>>() {};
						List<GHContent> contents = om.convertValue(o, contentList);
						return new GithubDirectoryAPI(path, ec.getDirectoryPage(owner, reponame, filepath, contents,  authentication));
					} else {
						// it's content.
						Format f = formatSupplier.getFormatFor(path);

						if (f instanceof DiagramFileFormat) {
							return createDiagramApi(u, f, false);
						} else {
							return createRegularFileApI(u, f, false);
						}
					}
				} catch (WebClientResponseException e) {
					if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
						Format f = formatSupplier.getFormatFor(path);
						if (f == null) {
							throw new ResponseStatusException(HttpStatus.NOT_FOUND);
						} else {
							return createDiagramApi(u,f, true);
						}
					} else {
						throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
					}
				}
				
			}
		} else {
			return null;
		}	
	}

	protected SourceAPI createRegularFileApI(K9URI u, Format f2, boolean isNew) throws URISyntaxException {
		K9MediaType mainMediaType = f2.getMediaTypes().get(0);
		return new AbstractGithubModifiableFileAPI(u, clientRepository, mainMediaType, isNew) {
			
			@Override
			public GitHub getGitHubAPI(Authentication a) {
				return createGitHub(getAccessToken(a, clientRepository));
			}
			
			@Override
			public String getUserId(Authentication a) {
				return AbstractGithubFileAPI.getEmail(a);
			}
		};
	}

	protected SourceAPI createDiagramApi(K9URI u, Format f2, boolean isNew) throws URISyntaxException {
		K9MediaType mainMediaType = f2.getMediaTypes().get(0);
		return new GithubDiagramFileAPI(u, clientRepository, (DiagramFileFormat) f2, mainMediaType, isNew) {
			
			@Override
			public GitHub getGitHubAPI(Authentication a) {
				return createGitHub(getAccessToken(a, clientRepository));
			}

			@Override
			public String getUserId(Authentication a) {
				return getEmail(a);
			}

			@Override
			public void addMeta(MetaReadWrite adl) {
				super.addMeta(adl);
				List<UserMeta> subscribers = broadcaster.getCurrentSubscribers(adl.getTopicUri());
				adl.setCollaborators(subscribers);
			}

			
		};
	}

	public GitHub createGitHub(String token) {
		try {
			return new GitHubBuilder().withOAuthToken(token).build();
		} catch (IOException e) {
			throw new Kite9XMLProcessingException("Couldn't get handle to github", e);
		}
	}
	
	public GitHub createGitHub(Authentication p) {
		String token = AbstractGithubFileAPI.getAccessToken(p, clientRepository);
		return createGitHub(token);
	}
	
	@Override
	public SourceAPI createAPI(Update u, Authentication a) throws Exception {
		return createAPI(u.getUri(), a);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ec = new AbstractGithubEntityConverter(formatSupplier) {

			@Override
			public LinkBuilder linkToRemappedURI() {
				return RelativeHostLinkBuilder.linkToCurrentMapping();
			}

			@Override
			protected GitHub getGithubApi(Authentication authentication) throws Exception {
				return GithubSourceAPIFactory.this.createGitHub(authentication);
			}
			
			
		};
		
		om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	}

}