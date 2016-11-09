//package com.CouponSystem.Service;
//
//
////import com.fasterxml.jackson.annotation.JsonAutoDetect;
////import com.fasterxml.jackson.annotation.JsonInclude;
////import com.fasterxml.jackson.annotation.PropertyAccessor;
////import com.fasterxml.jackson.databind.DeserializationFeature;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.fasterxml.jackson.databind.SerializationFeature;
////import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
////
////import javax.ws.rs.Produces;
////import javax.ws.rs.core.MediaType;
////import javax.ws.rs.ext.Provider;
////
////@Provider
////@Produces(MediaType.APPLICATION_JSON)
////public class ObjectMapperResolver extends JacksonJaxbJsonProvider {
////
////    private static ObjectMapper mapper = new ObjectMapper();
////
////    static {
////        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
////        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
////        mapper.enable(SerializationFeature.INDENT_OUTPUT);
////     }
////
////    public CustomJsonProvider() {
////        super();
////        setMapper(mapper);
////    }
////}
//
//import javax.ws.rs.ext.ContextResolver;
//import javax.ws.rs.ext.Provider;
//
//import com.fasterxml.jackson.databind.AnnotationIntrospector;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
//import com.fasterxml.jackson.databind.type.TypeFactory;
//import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
//
///**
// * TODO javadoc.
// *
// * @author Jakub Podlesak (jakub.podlesak at oracle.com)
// */
//@Provider
//public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {
//
//    final ObjectMapper defaultObjectMapper;
//    final ObjectMapper combinedObjectMapper;
//
//    public ObjectMapperResolver() {
//        defaultObjectMapper = createDefaultMapper();
//        combinedObjectMapper = createCombinedObjectMapper();
//    }
//
//    @Override
//    public ObjectMapper getContext(final Class<?> type) {
//
//        if (type == CombinedAnnotationBean.class) {
//            return combinedObjectMapper;
//        } else {
//            return defaultObjectMapper;
//        }
//    }
//
//    private static ObjectMapper createCombinedObjectMapper() {
//        return new ObjectMapper()
//                .configure(SerializationFeature.WRAP_ROOT_VALUE, true)
//                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true)
//                .setAnnotationIntrospector(createJaxbJacksonAnnotationIntrospector());
//    }
//
//    private static ObjectMapper createDefaultMapper() {
//        final ObjectMapper result = new ObjectMapper();
//        result.enable(SerializationFeature.INDENT_OUTPUT);
//
//        return result;
//    }
//
//    private static AnnotationIntrospector createJaxbJacksonAnnotationIntrospector() {
//
//        final AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
//        final AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();
//
//        return AnnotationIntrospector.pair(jacksonIntrospector, jaxbIntrospector);
//    }
//}