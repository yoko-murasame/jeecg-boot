/*
 Navicat Premium Data Transfer

 Source Server         : EPCTest
 Source Server Type    : PostgreSQL
 Source Server Version : 130002
 Source Host           : 120.27.214.53:54321
 Source Catalog        : bjcbd
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 130002
 File Encoding         : 65001

 Date: 02/12/2023 15:27:42
*/


-- ----------------------------
-- Table structure for app_authorize
-- ----------------------------
DROP TABLE IF EXISTS "public"."app_authorize";
CREATE TABLE "public"."app_authorize" (
  "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "app_key" varchar(255) COLLATE "pg_catalog"."default",
  "app_secret" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of app_authorize
-- ----------------------------
INSERT INTO "public"."app_authorize" VALUES ('1', 'cf74f1bdfa1644809f8b3afeff9f1dfb', 'ee0d1843057d41aeae3e34f819bb34d7b35a3eb083b2bb40');

-- ----------------------------
-- Primary Key structure for table app_authorize
-- ----------------------------
ALTER TABLE "public"."app_authorize" ADD CONSTRAINT "app_authorize_pkey" PRIMARY KEY ("id");
