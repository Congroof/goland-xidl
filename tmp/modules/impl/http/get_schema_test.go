/*-----------------------------------------------------------------------------------
 * Kingsoft Office Enterprise Cloud Services.
 * Copyright (c) Kingsoft Office,  All rights reserved.
 *-----------------------------------------------------------------------------------*/

package http

import (
	. "github.com/smartystreets/goconvey/convey"
	"ksogit.kingsoft.net/docmini/fx/xfx/monkey"
	"ksogit.kingsoft.net/docmini/fx/xfx/testsuite"
	"testing"
)

type GetSchemaSuite struct {
	testsuite.Suite
	Tag1HttpImplSuite
}

func TestGetSchemaSuite(t *testing.T) {
	testsuite.Run(t, new(GetSchemaSuite))
}

func (s *GetSchemaSuite) Test() {
	ins := s.Intance()
	_ = ins
	patches := monkey.NewPatches()
	defer patches.Reset()

	Convey("GetSchema", s.T(), func() {
		Convey("case 1", func() {

		})
	})
}
