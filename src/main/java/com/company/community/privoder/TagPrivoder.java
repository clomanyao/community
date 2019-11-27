package com.company.community.privoder;

import com.company.community.dto.TagManagerDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TagPrivoder {

    
    public static List<TagManagerDTO>  getTag(){
        List<TagManagerDTO> managerDTOList=new ArrayList<>();

        TagManagerDTO devLanguage = new TagManagerDTO();
        devLanguage.setTagManager("开发语言");
        devLanguage.setTagDTOList(Arrays.asList("javaScript","java","c++","python","c","c#","node.js","php","objective-c","shell","html5","go","css","swift"));
        managerDTOList.add(devLanguage);

        TagManagerDTO framework = new TagManagerDTO();
        framework.setTagManager("平台语言");
        framework.setTagDTOList(Arrays.asList("laravel","spring","express","django","springmvc","springBoot","hibernate","struts2","mybatis","dubbo","zookeeper","springCloud","springData"));
        managerDTOList.add(framework);

        TagManagerDTO server = new TagManagerDTO();
        server.setTagManager("服务器");
        server.setTagDTOList(Arrays.asList("netty","linux","hadoop","unix","负载均衡","docker","centos","tomcat","缓存","ubuntu","nginx"));
        managerDTOList.add(server);

        TagManagerDTO sqlLanguage = new TagManagerDTO();
        sqlLanguage.setTagManager("数据库和缓存");
        sqlLanguage.setTagDTOList(Arrays.asList("mysql","sqlserver","redis","oracle","sqlite","mongodb","nosql"));
        managerDTOList.add(sqlLanguage);

        TagManagerDTO devtools = new TagManagerDTO();
        devtools.setTagManager("开发工具");
        devtools.setTagDTOList(Arrays.asList("git","github","maven","eclipse","myeclipse","intellij-idea","svn","vim"));
        managerDTOList.add(devtools);

        TagManagerDTO others = new TagManagerDTO();
        others.setTagManager("其他");
        others.setTagDTOList(Arrays.asList("txt"));
        managerDTOList.add(others);
        return managerDTOList;
    }

    public static String conditionalTags(String tag){
        String badtag=null;
        String[] split = tag.split(",");
        List<String> taglist=Arrays.asList(split);

        List<String> dbtaglist= new ArrayList<>();
        List<TagManagerDTO> tagManagerDTOS = getTag();
        for (TagManagerDTO managerDTO : tagManagerDTOS) {
            for (String dbtag : managerDTO.getTagDTOList()) {
                dbtaglist.add(dbtag);
            }
        }
        for (String s : taglist) {
            if(!dbtaglist.contains(s)){
                badtag=s;
                break;
            }
        }
        return badtag;
    }
}
