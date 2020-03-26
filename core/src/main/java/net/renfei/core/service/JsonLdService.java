package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.PostsDTO;
import net.renfei.dao.entity.WeiboDOS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class JsonLdService extends BaseService {
    @Autowired
    private GlobalService globalService;

    private String getCommonTop() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+08:00'");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"@context\": \"http://schema.org/\",");
        sb.append("\"@graph\": [");
        sb.append("{");
        sb.append("\"@type\": \"Organization\",");
        sb.append("\"logo\": \"https://cdn.renfei.net/logo/logo_112.png\",");
        sb.append("\"url\": \"https://www.renfei.net\"");
        sb.append("},");
        sb.append("{");
        sb.append("\"@type\": \"WebSite\",");
        sb.append("\"url\": \"https://www.renfei.net\",");
        sb.append("\"potentialAction\": {");
        sb.append("\"@type\": \"SearchAction\",");
        sb.append("\"target\": \"https://www.renfei.net/search/?q={search_term_string}\",");
        sb.append("\"query-input\": \"required name=search_term_string\"");
        sb.append("}");
        sb.append("},");
        sb.append("{");
        sb.append("\"@type\": \"Person\",");
        sb.append("\"name\": \"任霏\",");
        sb.append("\"url\": \"https://www.renfei.net\",");
        sb.append("\"sameAs\": [");
        sb.append("\"https://github.com/NeilRen\",");
        sb.append("\"https://www.facebook.com/renfeii\",");
        sb.append("\"https://twitter.com/renfeii\",");
        sb.append("\"https://www.youtube.com/channel/UCPsjiVvFMS7piLgC-RHBWxg\"");
        sb.append("]");
        sb.append("},");
        sb.append("{");
        return sb.toString();
    }

    public String getJsonld(PostsDTO postsDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+08:00'");
        StringBuilder sb = new StringBuilder();
        sb.append(getCommonTop());
        sb.append("\"@type\": \"NewsArticle\",");
        sb.append("\"dateModified\":\"" + sdf.format(postsDTO.getReleaseTime()) + "\",");
        sb.append("\"datePublished\":\"" + sdf.format(postsDTO.getReleaseTime()) + "\",");
        sb.append("\"headline\":\"" + postsDTO.getTitle().replace("\"", "") + "\",");
        sb.append("\"image\":\"" + (postsDTO.getFeaturedImage() == null ? "https://cdn.renfei.net/logo/ogimage.png" : postsDTO.getFeaturedImage()) + "\",");
        sb.append("\"author\":{");
        sb.append("\"@type\": \"Person\",");
        sb.append("\"name\": \"" + (postsDTO.getSourceName() == null ? "任霏" : postsDTO.getSourceName()) + "\"");
        sb.append("},");
        sb.append("\"publisher\":{");
        sb.append("\"@type\": \"Organization\",");
        sb.append("\"name\": \"任霏博客\",");
        sb.append("\"logo\": {");
        sb.append("\"@type\": \"ImageObject\",");
        sb.append("\"url\": \"https://cdn.renfei.net/logo/logo_112.png\"");
        sb.append("}");
        sb.append("},");
        sb.append("\"description\": \"" + postsDTO.getDescribes() + "\",");
        sb.append("\"mainEntityOfPage\": {");
        sb.append("\"@type\":\"WebPage\",");
        sb.append("\"@id\":\"" + globalService.getDomain() + "/posts/" + postsDTO.getId() + "\"");
        sb.append("},");
        sb.append("\"speakable\": {");
        sb.append("\"@type\": \"SpeakableSpecification\",");
        sb.append("\"xpath\": [");
        sb.append("\"/html/head/title\",");
        sb.append("\"/html/head/meta[@name='description']/@content\"");
        sb.append("]");
        sb.append("}");
        sb.append("}");
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    public String getJsonld(WeiboDOS weiboDOS) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+08:00'");
        StringBuilder sb = new StringBuilder();
        sb.append(getCommonTop());
        sb.append("\"@type\": \"NewsArticle\",");
        sb.append("\"dateModified\":\"" + sdf.format(weiboDOS.getReleaseTime()) + "\",");
        sb.append("\"datePublished\":\"" + sdf.format(weiboDOS.getReleaseTime()) + "\",");
        sb.append("\"headline\":\"" + getTitle(weiboDOS.getContent()) + "\",");
        sb.append("\"image\":\"" + (weiboDOS.getImg() == null ? "https://cdn.renfei.net/logo/ogimage.png" : weiboDOS.getImg()) + "\",");
        sb.append("\"author\":{");
        sb.append("\"@type\": \"Person\",");
        sb.append("\"name\": \"任霏\"");
        sb.append("},");
        sb.append("\"publisher\":{");
        sb.append("\"@type\": \"Organization\",");
        sb.append("\"name\": \"任霏博客\",");
        sb.append("\"logo\": {");
        sb.append("\"@type\": \"ImageObject\",");
        sb.append("\"url\": \"https://cdn.renfei.net/logo/logo_112.png\"");
        sb.append("}");
        sb.append("},");
        sb.append("\"description\": \"" + weiboDOS.getContent() + "\",");
        sb.append("\"mainEntityOfPage\": {");
        sb.append("\"@type\":\"WebPage\",");
        sb.append("\"@id\":\"" + globalService.getDomain() + "/weibo/" + weiboDOS.getId() + "\"");
        sb.append("},");
        sb.append("\"speakable\": {");
        sb.append("\"@type\": \"SpeakableSpecification\",");
        sb.append("\"xpath\": [");
        sb.append("\"/html/head/title\",");
        sb.append("\"/html/head/meta[@name='description']/@content\"");
        sb.append("]");
        sb.append("}");
        sb.append("}");
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    private String getTitle(String content) {
        if (content.length() < 100) {
            return content;
        } else {
            return content.substring(0, 100);
        }
    }
}
