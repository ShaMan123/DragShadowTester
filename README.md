# DragShadowTester
App reproducing android `View.updateDragShadow` not updating the shadow's size


## Steps To Reproduce
Simply drag a view for a few seconds to see that although the shadow is updating it's size remains the same.
This is caused because `updateDragShadow` doesn't call `onProvideShadowMetrics`.
You can see debug logs with this tag: `DragShadowBuilder`

### [Bug Report](https://github.com/ShaMan123/DragShadowTester/tree/master/bug%20report/bugreport-Pixel_XL_API_R-2020-02-26-12-34-50-185b4b7c-fd78-44d6-aa68-22e1c6ca27cd)
