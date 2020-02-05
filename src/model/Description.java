package model;

public class Description {
    String[] desc;
    public Description(String line){
        String[] splitline = line.split(" ");
        desc = new String[5];
        System.arraycopy(splitline,1, desc,0 , splitline.length-1);
    }

    public String[] getDescription() {
        return desc;
    }

    public void setDescription(String[] desc) {
        this.desc = desc;
    }

}
