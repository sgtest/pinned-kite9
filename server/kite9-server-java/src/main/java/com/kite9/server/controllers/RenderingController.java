package com.kite9.server.controllers;

import com.kite9.pipeline.adl.format.FormatSupplier;
import com.kite9.pipeline.adl.format.media.K9MediaType;
import com.kite9.pipeline.adl.format.media.Kite9MediaTypes;
import com.kite9.server.sources.SourceAPI;
import com.kite9.server.sources.SourceAPIFactory;
import com.kite9.server.update.Update;
import com.kite9.server.uri.URIWrapper;
import com.kite9.server.web.URIRewriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static com.kite9.pipeline.adl.format.media.Kite9MediaTypes.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

/**
 * Handles rendering of Kite9 ADL content.  A bit like a simpler version of the CommandController, 
 * except that it just renders the ADL.
 * 
 * @author robmoffat
 *
 */
@Controller
public class RenderingController extends AbstractNegotiatingController {
	
	public RenderingController(FormatSupplier fs, SourceAPIFactory factory) {
		super(fs, factory);
	}

	@GetMapping(path= {"/api/renderer", "api/renderer.svg"}, produces= {SVG_VALUE})
	public ResponseEntity<?> renderSVG(
			RequestEntity<?> request,
			@RequestParam("uri") String uri, 
			@RequestHeader HttpHeaders headers) throws Exception {
		return contentNegotiation(request, URIWrapper.wrap(new URI(uri)), URIRewriter.getCompleteCurrentRequestURI(), headers, Arrays.asList(Kite9MediaTypes.INSTANCE.getSVG()), null);
	}
	
	@GetMapping(path= {"/api/renderer", "api/renderer.adl"}, produces= {ADL_SVG_VALUE})
	public ResponseEntity<?> renderADL(
			RequestEntity<?> request,
			@RequestParam("uri") String uri, 
			@RequestHeader HttpHeaders headers) throws Exception {
		return contentNegotiation(request, URIWrapper.wrap(new URI(uri)), URIRewriter.getCompleteCurrentRequestURI(), headers, Arrays.asList(Kite9MediaTypes.INSTANCE.getADL_SVG()), null);
	}
	
	@GetMapping(path= {"/api/renderer", "api/renderer.png"}, produces= {IMAGE_PNG_VALUE})
	public ResponseEntity<?> renderPNG(
			RequestEntity<?> request,
			@RequestParam("uri") String uri, 
			@RequestHeader HttpHeaders headers) throws Exception {
		return contentNegotiation(request, URIWrapper.wrap(new URI(uri)), URIRewriter.getCompleteCurrentRequestURI(), headers, Arrays.asList(Kite9MediaTypes.INSTANCE.getPNG()), null);
	}

	@PostMapping(path="/api/renderer", consumes= {ADL_SVG_VALUE}, 
		produces= {SVG_VALUE, EDITABLE_SVG_VALUE, IMAGE_PNG_VALUE, ADL_SVG_VALUE, TEXT_HTML_VALUE})
	public ResponseEntity<?> postEcho(
			@RequestBody byte[] adlSvg, 
			RequestEntity<?> request,
			NativeWebRequest webRequest,
			@RequestHeader HttpHeaders headers) throws Exception {
		String encoded = Base64.getEncoder().encodeToString(adlSvg);
		Update u = new Update(Collections.emptyList(), URIRewriter.getCompleteCurrentRequestURI(), encoded, Update.Type.NEW);
		SourceAPI s = getSourceAPI(u, null);
		List<K9MediaType> accepted = getMediaTypes(webRequest);
		return contentNegotiation(request, s, URIRewriter.getCompleteCurrentRequestURI(), headers, accepted, null);

	}

}