package com.example.bachelor.dto.metadata;


public class ReturnMetaData {

    public static class Builder {

        private long _id;
        private Object information;
        private String owner;
        private String group;

        public Builder(){}

        public Builder withID(long _id) {
            this._id = _id;

            return this;
        }

        public Builder withInformation(Object information) {
            this.information = information;

            return this;
        }

        public Builder withOwner(String owner){
            this.owner=owner;

            return this;
        }

        public Builder withGroup(String group){
            this.group=group;

            return this;
        }

        public ReturnMetaData build() {
            ReturnMetaData returnMetaData = new ReturnMetaData();
            returnMetaData._id = this._id;
            returnMetaData.information = this.information;
            returnMetaData.group=this.group;
            returnMetaData.owner=this.owner;
            return returnMetaData;
        }

    }


    public Long _id;
    public Object information;
    public String owner;
    public String group;

    private ReturnMetaData() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Object getInformation() {
        return information;
    }

    public void setInformation(Object information) {
        this.information = information;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
