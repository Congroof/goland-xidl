package middlewares

import (
	"ksogit.kingsoft.net/docmini/fx/xfx"
	"ksogit.kingsoft.net/docmini/fx/xfx/endpoint"
)

func (m *M1) Middleware(app xfx.App, endpointSchema xfx.EndpointSchema) endpoint.Middleware {
	// return func(next endpoint.Endpoint) endpoint.Endpoint {
	// 	return func(w http.ResponseWriter, r *http.Request) error {
	// 		do something...
	// 		return next(w, r)
	// 	}
	// }
	return nil
}
