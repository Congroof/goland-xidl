package modules

import (
	"ksogit.kingsoft.net/docmini/mapis/interceptor"
	common_tag1 "ksogit.kingsoft.net/docmini/mapis/tag1"
)

var _ interceptor.ApiInfo

func init() {
	common_tag1.RegisterInterceptor(&interceptor.SlowLogInterceptorSchema{})
	common_tag1.RegisterInterceptor(&interceptor.MetricsInterceptorSchema{})
	common_tag1.RegisterInterceptor(&interceptor.TracerInterceptorSchema{})
	common_tag1.RegisterInterceptor(&interceptor.BreakerInterceptorSchema{})
}
