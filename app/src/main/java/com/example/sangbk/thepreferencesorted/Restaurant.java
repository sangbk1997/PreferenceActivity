package com.example.sangbk.thepreferencesorted;

/**
 * Created by Sangbk on 10/20/2017.
 */

public class Restaurant {
    /**
     * Created by Sangbk on 10/18/2017.
     */

        private String name="";
        private String address="";
        private String type="";
        private String sale="";
        private String notes="";

        public String getName() {
            return(name);
        }

        public void setName(String name) {
            this.name=name;
        }

        public String getAddress() {
            return(address);
        }

        public void setAddress(String address) {
            this.address=address;
        }

        public String getType() {
            return(type);
        }

        public void setType(String type) {
            this.type=type;
        }
        public void setSale(String sale) {
            this.sale = sale;
        }
        public String getSale(){
            return (sale);
        }
        public String getNotes(){
            return notes;
        }
        public void setNotes(String notes){
            this.notes=notes;
        }
}
