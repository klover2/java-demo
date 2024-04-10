module.exports = {
  apps : [{
    name: "xxl-job-demo",
    script: 'java',
    args: '-jar /root/workspace/java-demo/xxl-job-demo/xxl-job-demo-1.0.0.jar',
    instances: 1,
    exec_mode: 'fork',
    watch: true,
    max_memory_restart: '100M', // 最大内存限制数，超出自动重启
    min_uptime: '60s', // 应用运行少于时间被认为是异常启动
    max_restarts: 3, // 最大异常重启次数，即小于min_uptime运行时间重启次数；
  }]
};
