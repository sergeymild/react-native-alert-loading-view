#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(AlertLoading, NSObject)

RCT_EXTERN_METHOD(showLoading:(NSDictionary *)params)
RCT_EXTERN_METHOD(hideLoading:(NSDictionary *)params)

@end
