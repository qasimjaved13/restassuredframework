package Base;

public class baseClass {
    public String testdatafilepath;
    public String basepath;


    public baseClass(){
        this.testdatafilepath = System.getProperty("user.dir") + "/src/test/TestData/testdata.xlsx";
        this.basepath= "https://reqres.in/api";
    }
}
