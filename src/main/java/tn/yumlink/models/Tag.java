package tn.yumlink.models;

public class Tag {
    private int tag_id;
    private String tag_value;
    private int tag_nb_usage;
    public Tag(){}

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_value() {
        return tag_value;
    }

    public void setTag_value(String tag_value) {
        this.tag_value = tag_value;
    }

    public int getTag_nb_usage() {
        return tag_nb_usage;
    }

    public void setTag_nb_usage(int tag_nb_usage) {
        this.tag_nb_usage = tag_nb_usage;
    }

    @Override
    public String toString() {
        return "#" + tag_value;
    }
}
