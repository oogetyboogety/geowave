// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GeoWaveVectorQuery.proto

package mil.nga.giat.geowave.service.grpc.protobuf;

public interface FeatureOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Feature)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>map&lt;string, string&gt; attributes = 1;</code>
   */
  int getAttributesCount();
  /**
   * <code>map&lt;string, string&gt; attributes = 1;</code>
   */
  boolean containsAttributes(
      java.lang.String key);
  /**
   * Use {@link #getAttributesMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getAttributes();
  /**
   * <code>map&lt;string, string&gt; attributes = 1;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getAttributesMap();
  /**
   * <code>map&lt;string, string&gt; attributes = 1;</code>
   */

  java.lang.String getAttributesOrDefault(
      java.lang.String key,
      java.lang.String defaultValue);
  /**
   * <code>map&lt;string, string&gt; attributes = 1;</code>
   */

  java.lang.String getAttributesOrThrow(
      java.lang.String key);
}