package Base;

public class baseClass {
    public String testdatafilepath;

    public baseClass(){
        this.testdatafilepath = System.getProperty("user.dir") + "/src/test/TestData/testdata.xlsx";
    }
}
