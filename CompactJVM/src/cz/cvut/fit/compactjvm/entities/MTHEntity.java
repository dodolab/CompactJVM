package cz.cvut.fit.compactjvm.entities;

/**
 *
 * @author Adam Vesecky
 */
public class MTHEntity {
    
    public int accessFlags;
    public int nameIndex;
    public int descriptorIndex;
    public int attributesCount;
    
    public Attribute[] attrs;
    
    public AttrCode getCodeAttribute() {
         for(int i = 0; i < attributesCount; ++i) {
             if(attrs[i] instanceof AttrCode) {
                 return (AttrCode) attrs[i];
             }
         }
         //throw new Exception("No Code attribute in method >>"+nameIndex+"<<"); 
         return null;
         //@todo nejaka exception poradna
    }
}
