"use strict";
/*
 * ATTENTION: An "eval-source-map" devtool has been used.
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file with attached SourceMaps in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
(() => {
var exports = {};
exports.id = "pages/index";
exports.ids = ["pages/index"];
exports.modules = {

/***/ "react":
/*!************************!*\
  !*** external "react" ***!
  \************************/
/***/ ((module) => {

module.exports = require("react");

/***/ }),

/***/ "react/jsx-dev-runtime":
/*!****************************************!*\
  !*** external "react/jsx-dev-runtime" ***!
  \****************************************/
/***/ ((module) => {

module.exports = require("react/jsx-dev-runtime");

/***/ }),

/***/ "axios":
/*!************************!*\
  !*** external "axios" ***!
  \************************/
/***/ ((module) => {

module.exports = import("axios");;

/***/ }),

/***/ "./pages/index.js":
/*!************************!*\
  !*** ./pages/index.js ***!
  \************************/
/***/ ((__webpack_module__, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.a(__webpack_module__, async (__webpack_handle_async_dependencies__, __webpack_async_result__) => { try {\n__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   \"default\": () => (__WEBPACK_DEFAULT_EXPORT__)\n/* harmony export */ });\n/* harmony import */ var react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react/jsx-dev-runtime */ \"react/jsx-dev-runtime\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react */ \"react\");\n/* harmony import */ var axios__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! axios */ \"axios\");\nvar __webpack_async_dependencies__ = __webpack_handle_async_dependencies__([axios__WEBPACK_IMPORTED_MODULE_2__]);\naxios__WEBPACK_IMPORTED_MODULE_2__ = (__webpack_async_dependencies__.then ? (await __webpack_async_dependencies__)() : __webpack_async_dependencies__)[0];\n\n\n\n// import { useSWR } from \"swr\";\n// import \"../src/styles/style.css\";\n// import LabelList from \"../src/component/labelList\";\n// import KakaoLogin from \"../src/component/logInKakao\";\nconst fetcher = (url)=>axios__WEBPACK_IMPORTED_MODULE_2__[\"default\"].get(url).then((res)=>res.data);\nfunction HomePage() {\n    // const { data, error } = useSWR(\"/api/data\", fetcher);\n    const [user, setUser] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(null);\n    const kakaoLogin = ()=>{\n    // 카카오로그인\n    };\n    return /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"div\", {\n        children: [\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"h1\", {\n                children: \"카카오 로그인 테스트\"\n            }, void 0, false, {\n                fileName: \"/Users/young/Desktop/code/whatever-note/whatever-ver-05/FE/front/pages/index.js\",\n                lineNumber: 21,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"div\", {\n                children: /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"img\", {\n                    src: \"/images/kakao_login_medium_narrow.png\",\n                    alt: \"Kakao Login\"\n                }, void 0, false, {\n                    fileName: \"/Users/young/Desktop/code/whatever-note/whatever-ver-05/FE/front/pages/index.js\",\n                    lineNumber: 24,\n                    columnNumber: 9\n                }, this)\n            }, void 0, false, {\n                fileName: \"/Users/young/Desktop/code/whatever-note/whatever-ver-05/FE/front/pages/index.js\",\n                lineNumber: 23,\n                columnNumber: 7\n            }, this)\n        ]\n    }, void 0, true, {\n        fileName: \"/Users/young/Desktop/code/whatever-note/whatever-ver-05/FE/front/pages/index.js\",\n        lineNumber: 20,\n        columnNumber: 5\n    }, this);\n}\n/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (HomePage);\n\n__webpack_async_result__();\n} catch(e) { __webpack_async_result__(e); } });//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiLi9wYWdlcy9pbmRleC5qcy5qcyIsIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7OztBQUFpQztBQUNQO0FBQzFCLGdDQUFnQztBQUVoQyxvQ0FBb0M7QUFDcEMsc0RBQXNEO0FBQ3RELHdEQUF3RDtBQUV4RCxNQUFNRSxVQUFVLENBQUNDLE1BQVFGLGlEQUFTLENBQUNFLEtBQUtFLElBQUksQ0FBQyxDQUFDQyxNQUFRQSxJQUFJQyxJQUFJO0FBRTlELFNBQVNDLFdBQVc7SUFDbEIsd0RBQXdEO0lBQ3hELE1BQU0sQ0FBQ0MsTUFBTUMsUUFBUSxHQUFHViwrQ0FBUUEsQ0FBQyxJQUFJO0lBRXJDLE1BQU1XLGFBQWEsSUFBTTtJQUN2QixTQUFTO0lBQ1g7SUFFQSxxQkFDRSw4REFBQ0M7OzBCQUNDLDhEQUFDQzswQkFBRzs7Ozs7OzBCQUVKLDhEQUFDRDswQkFDQyw0RUFBQ0U7b0JBQUlDLEtBQUk7b0JBQXdDQyxLQUFJOzs7Ozs7Ozs7Ozs7Ozs7OztBQUk3RDtBQUVBLGlFQUFlUixRQUFRQSxFQUFDIiwic291cmNlcyI6WyJ3ZWJwYWNrOi8vZnJvbnQvLi9wYWdlcy9pbmRleC5qcz9iZWU3Il0sInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IHVzZVN0YXRlIH0gZnJvbSBcInJlYWN0XCI7XG5pbXBvcnQgYXhpb3MgZnJvbSBcImF4aW9zXCI7XG4vLyBpbXBvcnQgeyB1c2VTV1IgfSBmcm9tIFwic3dyXCI7XG5cbi8vIGltcG9ydCBcIi4uL3NyYy9zdHlsZXMvc3R5bGUuY3NzXCI7XG4vLyBpbXBvcnQgTGFiZWxMaXN0IGZyb20gXCIuLi9zcmMvY29tcG9uZW50L2xhYmVsTGlzdFwiO1xuLy8gaW1wb3J0IEtha2FvTG9naW4gZnJvbSBcIi4uL3NyYy9jb21wb25lbnQvbG9nSW5LYWthb1wiO1xuXG5jb25zdCBmZXRjaGVyID0gKHVybCkgPT4gYXhpb3MuZ2V0KHVybCkudGhlbigocmVzKSA9PiByZXMuZGF0YSk7XG5cbmZ1bmN0aW9uIEhvbWVQYWdlKCkge1xuICAvLyBjb25zdCB7IGRhdGEsIGVycm9yIH0gPSB1c2VTV1IoXCIvYXBpL2RhdGFcIiwgZmV0Y2hlcik7XG4gIGNvbnN0IFt1c2VyLCBzZXRVc2VyXSA9IHVzZVN0YXRlKG51bGwpO1xuXG4gIGNvbnN0IGtha2FvTG9naW4gPSAoKSA9PiB7XG4gICAgLy8g7Lm07Lm07Jik66Gc6re47J24XG4gIH07XG5cbiAgcmV0dXJuIChcbiAgICA8ZGl2PlxuICAgICAgPGgxPuy5tOy5tOyYpCDroZzqt7jsnbgg7YWM7Iqk7Yq4PC9oMT5cblxuICAgICAgPGRpdj5cbiAgICAgICAgPGltZyBzcmM9XCIvaW1hZ2VzL2tha2FvX2xvZ2luX21lZGl1bV9uYXJyb3cucG5nXCIgYWx0PVwiS2FrYW8gTG9naW5cIiAvPlxuICAgICAgPC9kaXY+XG4gICAgPC9kaXY+XG4gICk7XG59XG5cbmV4cG9ydCBkZWZhdWx0IEhvbWVQYWdlO1xuIl0sIm5hbWVzIjpbInVzZVN0YXRlIiwiYXhpb3MiLCJmZXRjaGVyIiwidXJsIiwiZ2V0IiwidGhlbiIsInJlcyIsImRhdGEiLCJIb21lUGFnZSIsInVzZXIiLCJzZXRVc2VyIiwia2FrYW9Mb2dpbiIsImRpdiIsImgxIiwiaW1nIiwic3JjIiwiYWx0Il0sInNvdXJjZVJvb3QiOiIifQ==\n//# sourceURL=webpack-internal:///./pages/index.js\n");

/***/ })

};
;

// load runtime
var __webpack_require__ = require("../webpack-runtime.js");
__webpack_require__.C(exports);
var __webpack_exec__ = (moduleId) => (__webpack_require__(__webpack_require__.s = moduleId))
var __webpack_exports__ = (__webpack_exec__("./pages/index.js"));
module.exports = __webpack_exports__;

})();