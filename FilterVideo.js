// import React from 'react';
// import {
//   requireNativeComponent,
//   findNodeHandle,
//   NativeModules,
// } from 'react-native';

// const FilterVideoView = requireNativeComponent('FilterVideo');

// export class FilterVideo extends React.PureComponent {
//   constructor(props) {
//     super(props);
//     this.refRoot = this.refRoot.bind(this);
//   }

//   getHandle() {
//     return findNodeHandle(this.root);
//   }

//   refRoot(root) {
//     this.root = root;
//   }

//   render() {
//     const { source, autoplay, ...others } = this.props;

//     const sourceName =
//       typeof this.props.source === 'string' ? this.props.source : undefined;
//     const sourceJson =
//       typeof this.props.source === 'string'
//         ? undefined
//         : JSON.stringify(this.props.source);

//     return (
//       <FilterVideoView
//         sourceName={sourceName}
//       />
//     );
//   }
// }
