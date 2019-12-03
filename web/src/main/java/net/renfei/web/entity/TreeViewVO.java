package net.renfei.web.entity;

import java.util.List;

public class TreeViewVO {
    private String text;
    private String data;
    private List<TreeViewVO> nodes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<TreeViewVO> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeViewVO> nodes) {
        this.nodes = nodes;
    }
}
