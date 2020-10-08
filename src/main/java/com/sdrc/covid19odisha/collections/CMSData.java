package com.sdrc.covid19odisha.collections;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
@Document
@Data
@NoArgsConstructor
public class CMSData implements Serializable {
	private static final long serialVersionUID = 1558538811474305739L;

	@Id
	private String id;

	private String cmsCode;
	private Map<String,Object> cmsData;
	
	/*private String title;
	private String description;
	private List<FilepathCaptionModel> filepathCaptionModel;
	private String videoLink;
	private String resourceType;
	private List<String> tags;
	private String submittedBy;
	private Date createdDate;
	private Date updatedDate;
	private Boolean isActive;
	private Boolean isApprove;
	private String firstName;
	private Boolean isGallery;
	private Boolean [;
	
	private String caption;
	private List<FilepathCaptionModel> filepathCaptionModel;
	private String videoLink;
	private String galleryType;
	private String submittedBy;
	private Date createdDate;
	private Date updatedDate;
	private Boolean isActive;
	private Boolean isApprove;
	private String firstName;
	*/

}
