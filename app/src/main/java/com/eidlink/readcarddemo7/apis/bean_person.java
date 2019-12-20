package com.eidlink.readcarddemo7.apis;

public class bean_person {


    /**
     * errorCode : 0
     * errorMsg : 查询成功
     * extra : {"res":{"idType":"01","classify":"1","name":"侯谨妍","sex":"女","nation":"汉","birthDate":"19860921","address":"北京市朝阳区酒仙桥路14号","idnum":"410402198609215528","signingOrganization":"北京市公安局朝阳分局","beginTime":"20170331","endTime":"20370331"},"picture":"574c66007e00320000ff851d5151513e710dd564f32667c50a6887dee5284e5b2945fab2fe520265d74584857b27d8b2fe4663e80200dd0b38ff0e909e1a8b9d394da99a8b82ef7138e30b7656bd47856f273fad09bbe5803151aed65251515a3e8b62efa9d783f1d4335b77744552d49bf76a43967a3038a6cee4f693e6b0d9d8de6d637a75a6b0ba4e4b31d24eb68fc2d6e72e0fb6ca82b10387d784f1521a9aff1fe0b091bb45d87dfd10fd7a1a43f9a134b72538f62a3cfcfd6ec6e43319e2f758cdc53ec30aea16a9fc09ea6b5a506e714759f72a89a162f3428b3597a3eb631b3c6e22575598b657b9ad4b4ccaccc930cb19bfe7b99c0c2c152850b339460a726da6bb3f71d878da3046ddcc1d451f06a2212fb4476ef4beb09b92f8507562ee6b31de471e8f7a3c9f78b0622bf83e3dfddf261a84a6be0679f08b3ccafdd21d9ca34f970e23db4338f6a8e1d16e8a8323396fccfefeacf46d426dfe50dd4f53a3c15c631b2a86f73051d49df3da9261ed8d6fc72ef13abf03ee1bc40e2d1159bad344f642b9e573fe40d552d96f8049fbce38b98aa6910e1f25bd4389583ed8a522d032cee51df22f6dd49a171bf828ce08cf570895a221cb29b39c8674e24ccee848aa2f827dda9d7047395a5833bdc631b97a6f5946f45459eb7002c18841f2583ae2abea866bb8ee4847cef24001334b0af9236880072f200714ad07deba11028c64ea369449b7e77a988ad9ced850549b444f64600c39279af62e58484b83288308c8216667d9bc7e7581bcdd8215a523b13138b37fb04f94ee4bebf3b607f892beca9bf97d23a5f29da87817f5bae5c2c96d379506e5ecfc8d2cca9417aca1827b81ff1978a2d59335ef7dfa54bcb5c11ecda0e823e1a1650c53a0163cd6387911ee184efc9b1a8db2ebc7dd09ee158ad2fbcc1af5e85fbab689aa8c1cc7aad7c9b93a3cda9faae7a9a200547646b0fb65c88a37bd06781b19c27353f6f276ae5132bfa9618e5bb9d872f4c1ac11901263120fe51502c6bcd3fe650fa5b67e6096455254df74e84ecc856a7a6e274471e0e421f5758b06bc7ab1fe412d12b31c874d746aaccd770d6239323f7ad305d4ab5fdf1e6e142df055d9e219c0a4ec22dddad0e191cf2dbf7935f082a788cbc2934f2d02b4e490b3ad2a592956120dbc8f37c713dac48e5993a9d548e0b1e3ed9bffb04a1a91ad868d0a16cb48cf715a3ece33541cb1582136971c96995a3150ea5eda8917a47ab22cf8bb09d100a980ca36ae51726d17cc3032885daa1b493539d729f51c5a0e8d93d860a382219fc67706df98ebe3a97f1ed821dcacacce445a3ea594b2b837e53621437847890c1db690dc5bc39b1c40cb698be55df4ac0debc5b7369055d3e9c3d4e1d2c2dd70b57bb42d4fbef7bcd9a7ae51c098d02936019ffe0eb8436979d541"}
     * status : true
     */

    private int errorCode;
    private String errorMsg;
    private ExtraBean extra;
    private String status;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ExtraBean {
        /**
         * res : {"idType":"01","classify":"1","name":"侯谨妍","sex":"女","nation":"汉","birthDate":"19860921","address":"北京市朝阳区酒仙桥路14号","idnum":"410402198609215528","signingOrganization":"北京市公安局朝阳分局","beginTime":"20170331","endTime":"20370331"}
         * picture : 574c66007e00320000ff851d5151513e710dd564f32667c50a6887dee5284e5b2945fab2fe520265d74584857b27d8b2fe4663e80200dd0b38ff0e909e1a8b9d394da99a8b82ef7138e30b7656bd47856f273fad09bbe5803151aed65251515a3e8b62efa9d783f1d4335b77744552d49bf76a43967a3038a6cee4f693e6b0d9d8de6d637a75a6b0ba4e4b31d24eb68fc2d6e72e0fb6ca82b10387d784f1521a9aff1fe0b091bb45d87dfd10fd7a1a43f9a134b72538f62a3cfcfd6ec6e43319e2f758cdc53ec30aea16a9fc09ea6b5a506e714759f72a89a162f3428b3597a3eb631b3c6e22575598b657b9ad4b4ccaccc930cb19bfe7b99c0c2c152850b339460a726da6bb3f71d878da3046ddcc1d451f06a2212fb4476ef4beb09b92f8507562ee6b31de471e8f7a3c9f78b0622bf83e3dfddf261a84a6be0679f08b3ccafdd21d9ca34f970e23db4338f6a8e1d16e8a8323396fccfefeacf46d426dfe50dd4f53a3c15c631b2a86f73051d49df3da9261ed8d6fc72ef13abf03ee1bc40e2d1159bad344f642b9e573fe40d552d96f8049fbce38b98aa6910e1f25bd4389583ed8a522d032cee51df22f6dd49a171bf828ce08cf570895a221cb29b39c8674e24ccee848aa2f827dda9d7047395a5833bdc631b97a6f5946f45459eb7002c18841f2583ae2abea866bb8ee4847cef24001334b0af9236880072f200714ad07deba11028c64ea369449b7e77a988ad9ced850549b444f64600c39279af62e58484b83288308c8216667d9bc7e7581bcdd8215a523b13138b37fb04f94ee4bebf3b607f892beca9bf97d23a5f29da87817f5bae5c2c96d379506e5ecfc8d2cca9417aca1827b81ff1978a2d59335ef7dfa54bcb5c11ecda0e823e1a1650c53a0163cd6387911ee184efc9b1a8db2ebc7dd09ee158ad2fbcc1af5e85fbab689aa8c1cc7aad7c9b93a3cda9faae7a9a200547646b0fb65c88a37bd06781b19c27353f6f276ae5132bfa9618e5bb9d872f4c1ac11901263120fe51502c6bcd3fe650fa5b67e6096455254df74e84ecc856a7a6e274471e0e421f5758b06bc7ab1fe412d12b31c874d746aaccd770d6239323f7ad305d4ab5fdf1e6e142df055d9e219c0a4ec22dddad0e191cf2dbf7935f082a788cbc2934f2d02b4e490b3ad2a592956120dbc8f37c713dac48e5993a9d548e0b1e3ed9bffb04a1a91ad868d0a16cb48cf715a3ece33541cb1582136971c96995a3150ea5eda8917a47ab22cf8bb09d100a980ca36ae51726d17cc3032885daa1b493539d729f51c5a0e8d93d860a382219fc67706df98ebe3a97f1ed821dcacacce445a3ea594b2b837e53621437847890c1db690dc5bc39b1c40cb698be55df4ac0debc5b7369055d3e9c3d4e1d2c2dd70b57bb42d4fbef7bcd9a7ae51c098d02936019ffe0eb8436979d541
         */

        private ResBean res;
        private String picture;

        public ResBean getRes() {
            return res;
        }

        public void setRes(ResBean res) {
            this.res = res;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public static class ResBean {
            @Override
            public String toString() {
                return "ResBean{" +
                        "idType='" + idType + '\'' +
                        ", classify='" + classify + '\'' +
                        ", name='" + name + '\'' +
                        ", sex='" + sex + '\'' +
                        ", nation='" + nation + '\'' +
                        ", birthDate='" + birthDate + '\'' +
                        ", address='" + address + '\'' +
                        ", idnum='" + idnum + '\'' +
                        ", signingOrganization='" + signingOrganization + '\'' +
                        ", beginTime='" + beginTime + '\'' +
                        ", endTime='" + endTime + '\'' +
                        '}';
            }

            /**
             * idType : 01
             * classify : 1
             * name : 侯谨妍
             * sex : 女
             * nation : 汉
             * birthDate : 19860921
             * address : 北京市朝阳区酒仙桥路14号
             * idnum : 410402198609215528
             * signingOrganization : 北京市公安局朝阳分局
             * beginTime : 20170331
             * endTime : 20370331
             */


            private String idType;
            private String classify;
            private String name;
            private String sex;
            private String nation;
            private String birthDate;
            private String address;
            private String idnum;
            private String signingOrganization;
            private String beginTime;
            private String endTime;

            public String getIdType() {
                return idType;
            }

            public void setIdType(String idType) {
                this.idType = idType;
            }

            public String getClassify() {
                return classify;
            }

            public void setClassify(String classify) {
                this.classify = classify;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getBirthDate() {
                return birthDate;
            }

            public void setBirthDate(String birthDate) {
                this.birthDate = birthDate;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getIdnum() {
                return idnum;
            }

            public void setIdnum(String idnum) {
                this.idnum = idnum;
            }

            public String getSigningOrganization() {
                return signingOrganization;
            }

            public void setSigningOrganization(String signingOrganization) {
                this.signingOrganization = signingOrganization;
            }

            public String getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }
        }
    }
}


