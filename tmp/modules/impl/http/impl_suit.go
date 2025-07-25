/*-----------------------------------------------------------------------------------
 * Kingsoft Office Enterprise Cloud Services.
 * Copyright (c) Kingsoft Office,  All rights reserved.
 *-----------------------------------------------------------------------------------*/

package http

import (
	core_test "tmp/modules/impl/core/test"
)

type Tag1HttpImplSuite struct {
	core_test.Tag1CoreImplSuite
	impl *Tag1HttpImpl
	//todo
}

func (s *Tag1HttpImplSuite) SetupSuite() {
	s.impl = s.Intance()
}

func (s *Tag1HttpImplSuite) Intance() *Tag1HttpImpl {
	httpImpl := &Tag1HttpImpl{
		Tag1CoreImpl: s.Tag1CoreImplSuite.Intance(),
		//todo
	}

	// todo
	return httpImpl
}
