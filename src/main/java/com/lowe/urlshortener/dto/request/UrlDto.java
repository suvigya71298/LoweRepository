package com.lowe.urlshortener.dto.request;

import com.fasterxml.jackson.annotation.JsonView;
import com.lowe.urlshortener.view.DtoView;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class UrlDto {
    @NotEmpty(groups = {DtoView.UrlDtoView.class})
    @JsonView({DtoView.UrlDtoView.class})
	private String url;
    @JsonView({DtoView.UrlDtoView.class})
    private String expirationDate;
}
