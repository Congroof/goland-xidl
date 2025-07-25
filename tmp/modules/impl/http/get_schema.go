/*-----------------------------------------------------------------------------------
 * Kingsoft Office Enterprise Cloud Services.
 * Copyright (c) Kingsoft Office,  All rights reserved.
 *-----------------------------------------------------------------------------------*/

package http

import (
	e "ksogit.kingsoft.net/docmini/fx/xfx/econtext"
	"ksogit.kingsoft.net/docmini/fx/xfx/el"
	"ksogit.kingsoft.net/docmini/mapis/tag1/httpdef"
)

var _ httpdef.Time

func (m *Tag1HttpImpl) GetSchema(ctx e.EContext, request *httpdef.GetSchemaRequest) (*httpdef.GetSchemaResponse, el.XError) {
	// TODO
	return nil, nil
}
