# DragShadowTester
App reproducing android `View.updateDragShadow` not updating the shadow's size


## Steps To Reproduce
Simply drag a view for a few seconds to see that although the shadow is updating it's size remains the same.
This is caused because `updateDragShadow` doesn't call `onProvideShadowMetrics`
