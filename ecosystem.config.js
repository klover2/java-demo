module.exports = {
  apps : [{
    name: "xxl-job-demo",
    script: 'java',
    args: '-jar /root/workspace/java-demo/xxl-job-demo/target/xxl-job-demo-1.0.0.jar',
    instances: 1,
    exec_mode: 'fork',
    watch: true,
  }]
};
