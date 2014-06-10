///**
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.apache.camel.example.cxf;
//
//import org.apache.cxf.frontend.ClientProxyFactoryBean;
//
//import fr.univ_lyon1.m2ti.services.paiement.PaiementPortTypeImpl;
//
//
//public class CamelRouteClient {
//
//    private static final String URL = "http://localhost:9000/camel_route/webservices/paiement";
//    
//    protected static PaiementPortTypeImpl createCXFClient() {
//        // we use CXF to create a client for us as its easier than JAXWS and works
//        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
//        factory.setServiceClass(PaiementPortTypeImpl.class);
//        factory.setAddress(URL);
//        return (PaiementPortTypeImpl) factory.create();
//    }
//
//    public static void main(String[] args) throws Exception {
//        CamelRouteClient client = new CamelRouteClient();
//        //client.runTest();
//    }
//
//}
