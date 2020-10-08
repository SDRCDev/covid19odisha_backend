package com.sdrc.covid19odisha.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sdrc.covid19odisha.collections.CMSData;
import com.sdrc.covid19odisha.collections.CmsType;
import com.sdrc.covid19odisha.collections.Gallery;
import com.sdrc.covid19odisha.collections.ImportantLinks;
import com.sdrc.covid19odisha.collections.Resources;
import com.sdrc.covid19odisha.collections.TypeDetail;
import com.sdrc.covid19odisha.models.CmsViewModel;
import com.sdrc.covid19odisha.models.FilepathCaptionModel;
import com.sdrc.covid19odisha.models.ImageModel;
import com.sdrc.covid19odisha.models.LanguagePreferenceModel;
import com.sdrc.covid19odisha.models.ResourceModel;
import com.sdrc.covid19odisha.models.SuccessStoriesModel;
import com.sdrc.covid19odisha.repositories.CmsTypeRepository;
import com.sdrc.covid19odisha.services.CmsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CMSController {
	
	@Autowired
	private CmsService cmsService;
	
	@Autowired
	private CmsTypeRepository cmsTypeRepository;
	
	@GetMapping(value="/saveCmsType")
	public String saveCMS(){
		cmsTypeRepository.deleteAll();
		CmsType cmsType = new CmsType();
		cmsType.setCmsTypeName("Resources");
		cmsType.setDescription("CMS Resource Type");
		cmsType.setCmsCode("cms001");
		cmsType.setTypeId(22);
		cmsTypeRepository.save(cmsType);
		cmsType = new CmsType();
		cmsType.setCmsTypeName("Gallery");
		cmsType.setDescription("CMS Gallery Type");
		cmsType.setCmsCode("cms002");
		cmsType.setTypeId(23);
		cmsTypeRepository.save(cmsType);
		return "Done";
	}
	
	
	@GetMapping("/getAllCmsType")
	public ResponseEntity<List<CmsType>> getAllCmsType(){
		return cmsService.getAllCmsType();
	}
	@GetMapping("/getCmsTypeDetails")
	public ResponseEntity<List<TypeDetail>> getCmsTypeDetails(@RequestParam(value="typeId") Integer typeId){
		return cmsService.getCmsTypeDetails(typeId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/uploadFiles")
	public ResponseEntity<String> uploadFiles(@RequestBody MultipartFile multipartfiles) {
		return cmsService.uploadFile(multipartfiles);

	}
	
	@PostMapping(value = "/saveCmsData")
	public ResponseEntity<String> SaveResource(@RequestBody CMSData cmsData) {
		return cmsService.saveCmsData(cmsData);
	}
	
	@GetMapping(value = "/getCmsTabularData")
	public ResponseEntity<CmsViewModel> getCmsTabularData(@RequestParam(value="cmsCode") String cmsCode) {
		return cmsService.getCmsTabularData(cmsCode);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteCmsData")
	public ResponseEntity<String> deleteCmsData(@RequestParam("id") String id) {
		return cmsService.deleteCmsData(id);
	}

	@ResponseBody
	@RequestMapping(value = "/approveCmsData")
	public ResponseEntity<String> approveCmsData(@RequestParam("id") String id) {
		return cmsService.approveCmsData(id);
	}
	
	 @ResponseBody
	 @RequestMapping(value="/getCmsDataDetails")
	 public Map<String,Object> getCmsDataDetails(@RequestParam("cmsDataId") String cmsDataId){
		 return cmsService.getCmsDataDetails(cmsDataId);
	 }
	 
	 
	 @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
		public void downLoad(@RequestParam("fileName") String name, HttpServletResponse response) throws IOException {

			InputStream inputStream;
			try {
				String fileName = name.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("%2C", ",")
						.replaceAll("\\+", " ").replaceAll("%20", " ").replaceAll("%26", "&").replaceAll("%5C", "/");
				inputStream = new FileInputStream(fileName);
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", new java.io.File(fileName).getName());
				response.setHeader(headerKey, headerValue);
				response.setContentType("application/octet-stream"); // for all file
																		// type
				ServletOutputStream outputStream = response.getOutputStream();
				FileCopyUtils.copy(inputStream, outputStream);
				inputStream.close();
				outputStream.flush();
				outputStream.close();
				// new File(fileName).delete();

			} catch (IOException e) {
				throw new RuntimeException();
			}
		}
	 @ResponseBody
		@RequestMapping(value = "/saveResource")
		public ResponseEntity<String> saveResource(@RequestBody ResourceModel resourceModel, OAuth2Authentication oauth) {
			return cmsService.saveResources(resourceModel, oauth);
		}
	 @ResponseBody
		@RequestMapping(value = "/getResourceTypeDetails")
		Map<String, List<TypeDetail>> getResourceTypeDetails() {
			return cmsService.getTypeDetail();
		}

		@ResponseBody
		@RequestMapping(value = "/getAllResources")
		public ResourceModel getAllResources(OAuth2Authentication oauth) {
			return cmsService.getAllResources(oauth);
		}

		@ResponseBody
		@RequestMapping(value = "/resourceUploadFile")
		public ResponseEntity<String> resourceuploadFile(@RequestBody MultipartFile file) {
			return cmsService.uploadFile(file);

		}

		@ResponseBody
		@RequestMapping(value = "/getResourceDetails")
		public List<FilepathCaptionModel> getResourceDetails(@RequestParam("resourceId") String resourceId) {
			return cmsService.getResourceDetails(resourceId);
		}

		@ResponseBody
		@RequestMapping(value = "/deleteResource")
		public ResponseEntity<String> deleteResource(@RequestParam("resourceId") String resourceId,
				OAuth2Authentication oauth) {
			return cmsService.deleteResource(resourceId, oauth);
		}

		@ResponseBody
		@RequestMapping(value = "/approveResource")
		public ResponseEntity<String> approveResource(@RequestParam("resourceId") String resourceId,
				OAuth2Authentication oauth) {
			return cmsService.approveResource(resourceId, oauth);
		}
		

		@ResponseBody
		@RequestMapping(value = "/saveGallery")
		public ResponseEntity<String> saveGallery(@RequestBody Gallery gallery, OAuth2Authentication oauth) {
			return cmsService.saveGallery(gallery, oauth);
		}

		@ResponseBody
		@RequestMapping(value = "/getAllGallery")
		public ResourceModel getAllGallery(OAuth2Authentication oauth) {
			return cmsService.getAllGallery(oauth);
		}

		@ResponseBody
		@RequestMapping(value = "/deleteGallery")
		public ResponseEntity<String> deleteGallery(@RequestParam("galleryId") String galleryId,
				OAuth2Authentication oauth) {
			return cmsService.deleteGallery(galleryId, oauth);
		}

		@ResponseBody
		@RequestMapping(value = "/approveGallery")
		public ResponseEntity<String> approveGallery(@RequestParam("galleryId") String galleryId,
				OAuth2Authentication oauth) {
			return cmsService.approveGallery(galleryId, oauth);
		}

		@ResponseBody
		@RequestMapping(value = "/getGalleryDetails")
		public List<FilepathCaptionModel> getGalleryDetails(@RequestParam("galleryId") String galleryId) {
			return cmsService.getGalleryDetails(galleryId);
		}
		
		
		@RequestMapping(value = "/downloadCmsDoc", method = RequestMethod.GET)
		public void downLoad(@RequestParam("fileName") String names,
				HttpServletResponse response, @RequestParam(value="inline", required=false) Boolean inline) throws IOException {

			InputStream inputStream;
			String fileName = "";

			try {
				log.info(fileName);
				fileName = names.trim().replaceAll("%3A", ":").replaceAll("%2F", "/")
								.replaceAll("%5C", "/").replaceAll("%2C", ",").replaceAll("\\\\", "/")
								.replaceAll("%20", " ").replaceAll("%26", "&")
								.replaceAll("\\+", " ").replaceAll("%22", "").replaceAll("%3F", "?").replaceAll("%3D", "=");
				
				inputStream = new FileInputStream(fileName);
				String headerKey = "Content-Disposition";
				String headerValue = "";
				
				if(inline!=null && inline) {
					headerValue= String.format("inline; filename=\"%s\"",
							new java.io.File(fileName).getName());
					response.setContentType("application/pdf"); // for pdf file
					// type
				}else {
					headerValue= String.format("attachment; filename=\"%s\"",
							new java.io.File(fileName).getName());
					response.setContentType("application/octet-stream"); // for all file
					// type
				}
					
				response.setHeader(headerKey, headerValue);
				
				ServletOutputStream outputStream = response.getOutputStream();
				FileCopyUtils.copy(inputStream, outputStream);
				inputStream.close();
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e) {
				log.error("FileNotFoundException",  e);
			} catch (IOException e) {
				log.error("IOException",  e);
			}
			
		}
		
		@ResponseBody
		@RequestMapping(value = "/getAllApprovedGalleryImage")
		public Map<String,List<ImageModel>> getAllApprovedGalleryImage() {
			return cmsService.getAllApprovedGalleryImage();
		}
		
		@RequestMapping(value = "/downloadAllFile", method = RequestMethod.GET)
		public void downloadAllFile(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {

			InputStream inputStream;
			try {
				String fileNames = fileName.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("%2C", ",")
						.replaceAll("\\+", " ").replaceAll("%20", " ").replaceAll("%26", "&").replaceAll("%5C", "/");
				inputStream = new FileInputStream(fileNames);
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", new java.io.File(fileNames).getName());
				response.setHeader(headerKey, headerValue);
				response.setContentType("application/octet-stream"); // for all file
																		// type
				ServletOutputStream outputStream = response.getOutputStream();
				FileCopyUtils.copy(inputStream, outputStream);
				inputStream.close();
				outputStream.flush();
				outputStream.close();
				// new File(fileName).delete();

			} catch (IOException e) {
				log.error("error-while downloading with payload : {}", fileName, e);
				throw new RuntimeException();
			}
		}
		
		
		@ResponseBody
		@RequestMapping(value = "/saveImportantLinks")
		public ResponseEntity<String> saveImportantLinks(@RequestBody ImportantLinks importantLinks,
				OAuth2Authentication oauth) {
			return cmsService.saveImportantLinks(importantLinks, oauth);
		}

		@ResponseBody
		@RequestMapping(value = "/getAllImportantLinks")
		public ResourceModel getAllImportantLinks(OAuth2Authentication oauth) {
			return cmsService.getAllImportantLinks(oauth);
		}

		@ResponseBody
		@RequestMapping(value = "/deleteImportantLinks")
		public ResponseEntity<String> deleteImportantLinks(@RequestParam("importantLinksId") String importantLinksId,
				OAuth2Authentication oauth) {
			return cmsService.deleteImportantLinks(importantLinksId, oauth);
		}

		@ResponseBody
		@RequestMapping(value = "/approveImportantLinks")
		public ResponseEntity<String> approveImportantLinks(@RequestParam("importantLinksId") String importantLinksId,
				OAuth2Authentication oauth) {
			return cmsService.approveImportantLinks(importantLinksId, oauth);
		}
		
		@ResponseBody
		@RequestMapping(value = "/getAllApprovedGallery")
		public List<Gallery> getAllApprovedGallery() {
			return cmsService.getAllApprovedGallery();
		}
		
		@ResponseBody
		@RequestMapping(value = "/getAllApprovedImportantLinks")
		public List<ImportantLinks>  getAllApprovedImportantLinks() {
			return cmsService.getAllApprovedImportantLinks();
		}
		
		@ResponseBody
		@RequestMapping(value = "/getAllApprovedResources")
		public Map<String,List<Resources>> getAllApprovedResource() {
			return cmsService.getAllApprovedResource();
		}
		
		

		 @ResponseBody
			@RequestMapping(value = "/saveSucessStories")
			public ResponseEntity<String> saveSucessStories(@RequestBody SuccessStoriesModel successStoriesModel,OAuth2Authentication oauth) {
				return cmsService.saveSucessStories(successStoriesModel,oauth);

			}
		 
		 
		 @ResponseBody
			@RequestMapping(value = "/uploadFile")
			public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) {
				return cmsService.uploadFile(file);

			}
		 
		 @ResponseBody
		 @RequestMapping(value="/getAllSuccessStories")
		 public SuccessStoriesModel getAllSuccessStories(OAuth2Authentication oauth){
			 return cmsService.getAllSuccessStories(oauth);
		 }

		 @ResponseBody
		 @RequestMapping(value="/getSuccessStoriesDetails")
		 public List<FilepathCaptionModel> getSuccessStoriesDetails(@RequestParam("storyId") String storyId){
			 return cmsService.getSuccessStoriesDetails(storyId);
		 }
		 
		 @ResponseBody
		 @RequestMapping(value="/deleteSuccessStory")
		 public ResponseEntity<String> deleteSuccessStory(@RequestParam("storyId") String storyId,OAuth2Authentication oauth){
			 return cmsService.deleteSuccessStory(storyId,oauth);
		 }
		 
		 
		 @ResponseBody
		 @RequestMapping(value="/approveSuccessStory")
		 public ResponseEntity<String> approveSuccessStory(@RequestParam("storyId") String storyId,OAuth2Authentication oauth){
			 return cmsService.approveSuccessStory(storyId,oauth);
		 }
		 
		 @ResponseBody
		 @RequestMapping(value="/getAllSuccessStoriesForHomePage")
		 public SuccessStoriesModel getAllSuccessStoriesForHomePage(OAuth2Authentication oauth){
			 return cmsService.getAllSuccessStoriesForHomePage(oauth);
		 }


		 @ResponseBody
			@RequestMapping(value = "/bypass/setLanguagePreference")
			public ResponseEntity<LanguagePreferenceModel> setLanguagePreference(@RequestParam("languageId") Integer languageId,@RequestParam("ip")
					String ip) {
				return cmsService.setLanguagePreference(ip,languageId);
			}

		 @ResponseBody
			@RequestMapping(value = "/bypass/getLanguagePreference")
			public ResponseEntity<LanguagePreferenceModel> getLanguagePreference(@RequestParam("ip")
					String ip) {
				return cmsService.getLanguagePreference(ip);
			}

}
