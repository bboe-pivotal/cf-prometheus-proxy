package bboe.prometheusproxy.cfprometheusproxy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MetricCleanerTest {
    private MetricCleaner metricCleaner = new MetricCleaner();

    @Test
    public void testCleanMessage() {
        String input =
                "# HELP firehose_value_metric_vxlan_policy_agent_container_metadata_time Cloud Foundry Firehose 'containerMetadataTime' value metric from 'vxlan-policy-agent'.\n" +
                        "# TYPE firehose_value_metric_vxlan_policy_agent_container_metadata_time gauge\n" +
                        "firehose_value_metric_vxlan_policy_agent_container_metadata_time{bosh_deployment=\"p-isolation-segment-8dcd8f15c50dd51a99cd\",bosh_job_id=\"e6ac649b-a539-4873-9e11-07cfa002b63a\",bosh_job_ip=\"192.168.3.40\",bosh_job_name=\"isolated_diego_cell\",environment=\"CloudNative.biz\",origin=\"vxlan-policy-agent\",placement_tag=\"myotherapps\",product=\"Isolation Segment\",source_id=\"vxlan-policy-agent\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"ms\"} 0.234532\n" +
                        "# HELP firehose_value_metric_vxlan_policy_agent_iptables_enforce_time Cloud Foundry Firehose 'iptablesEnforceTime' value metric from 'vxlan-policy-agent'.\n" +
                        "# TYPE firehose_value_metric_vxlan_policy_agent_iptables_enforce_time gauge\n" +
                        "firehose_value_metric_vxlan_policy_agent_iptables_enforce_time{bosh_deployment=\"p-isolation-segment-8dcd8f15c50dd51a99cd\",bosh_job_id=\"e6ac649b-a539-4873-9e11-07cfa002b63a\",bosh_job_ip=\"192.168.3.40\",bosh_job_name=\"isolated_diego_cell\",environment=\"CloudNative.biz\",origin=\"vxlan-policy-agent\",placement_tag=\"myotherapps\",product=\"Isolation Segment\",source_id=\"vxlan-policy-agent\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"ms\"} 0.001288\n" +
                        "# HELP firehose_value_metric_vxlan_policy_agent_memory_stats_last_gc_pause_time_ns Cloud Foundry Firehose 'memoryStats.lastGCPauseTimeNS' value metric from 'vxlan-policy-agent'.\n" +
                        "# TYPE firehose_value_metric_vxlan_policy_agent_memory_stats_last_gc_pause_time_ns gauge\n" +
                        "firehose_value_metric_vxlan_policy_agent_memory_stats_last_gc_pause_time_ns{bosh_deployment=\"p-isolation-segment-8dcd8f15c50dd51a99cd\",bosh_job_id=\"e6ac649b-a539-4873-9e11-07cfa002b63a\",bosh_job_ip=\"192.168.3.40\",bosh_job_name=\"isolated_diego_cell\",environment=\"CloudNative.biz\",origin=\"vxlan-policy-agent\",placement_tag=\"myotherapps\",product=\"Isolation Segment\",source_id=\"vxlan-policy-agent\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"count\"} 18318";
        String output =
                "# HELP firehose_value_metric_vxlan_policy_agent_container_metadata_time Cloud Foundry Firehose 'containerMetadataTime' value metric from 'vxlan-policy-agent'.\n" +
                        "# TYPE firehose_value_metric_vxlan_policy_agent_container_metadata_time gauge\n" +
                        "firehose_value_metric_vxlan_policy_agent_container_metadata_time{bosh_deployment=\"p-isolation-segment-8dcd8f15c50dd51a99cd\",bosh_job_id=\"e6ac649b-a539-4873-9e11-07cfa002b63a\",bosh_job_ip=\"192.168.3.40\",bosh_job_name=\"isolated_diego_cell\",environment=\"CloudNative.biz\",origin=\"vxlan-policy-agent\",placement_tag=\"myotherapps\",product=\"Isolation Segment\",source_id=\"vxlan-policy-agent\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"ms\"} 0.234532\n" +
                        "# HELP firehose_value_metric_vxlan_policy_agent_iptables_enforce_time Cloud Foundry Firehose 'iptablesEnforceTime' value metric from 'vxlan-policy-agent'.\n" +
                        "# TYPE firehose_value_metric_vxlan_policy_agent_iptables_enforce_time gauge\n" +
                        "firehose_value_metric_vxlan_policy_agent_iptables_enforce_time{bosh_deployment=\"p-isolation-segment-8dcd8f15c50dd51a99cd\",bosh_job_id=\"e6ac649b-a539-4873-9e11-07cfa002b63a\",bosh_job_ip=\"192.168.3.40\",bosh_job_name=\"isolated_diego_cell\",environment=\"CloudNative.biz\",origin=\"vxlan-policy-agent\",placement_tag=\"myotherapps\",product=\"Isolation Segment\",source_id=\"vxlan-policy-agent\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"ms\"} 0.001288\n" +
                        "# HELP firehose_value_metric_vxlan_policy_agent_memory_stats_last_gc_pause_time_ns Cloud Foundry Firehose 'memoryStats.lastGCPauseTimeNS' value metric from 'vxlan-policy-agent'.\n" +
                        "# TYPE firehose_value_metric_vxlan_policy_agent_memory_stats_last_gc_pause_time_ns gauge\n" +
                        "firehose_value_metric_vxlan_policy_agent_memory_stats_last_gc_pause_time_ns{bosh_deployment=\"p-isolation-segment-8dcd8f15c50dd51a99cd\",bosh_job_id=\"e6ac649b-a539-4873-9e11-07cfa002b63a\",bosh_job_ip=\"192.168.3.40\",bosh_job_name=\"isolated_diego_cell\",environment=\"CloudNative.biz\",origin=\"vxlan-policy-agent\",placement_tag=\"myotherapps\",product=\"Isolation Segment\",source_id=\"vxlan-policy-agent\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"count\"} 18318";
        assertEquals(output, metricCleaner.cleanMessage(input), "Simple test failed");
    }

    @Test
    public void testCleanMessage2() {
        String input =
                "# HELP promhttp_metric_handler_requests_total Total number of scrapes by HTTP status code.\n" +
                        "# TYPE promhttp_metric_handler_requests_total counter\n" +
                        "promhttp_metric_handler_requests_total{code=\"200\"} 5434\n" +
                        "# HELP firehose_value_metric_uaa_requests_global_unhealthy_time Cloud Foundry Firehose 'requests.global.unhealthy.time' value metric from 'uaa'.\n" +
                        "# TYPE firehose_value_metric_uaa_requests_global_unhealthy_time gauge\n" +
                        "firehose_value_metric_uaa_requests_global_unhealthy_time{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"uaa\",product=\"Small Footprint VMware Tanzu Application Service\",source_id=\"uaa\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"gauge\"} 2144\n" +
                        "# HELP firehose_counter_event_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '273ceeca-857e-46ff-87dd-3f4e7da552a6'.\n" +
                        "# TYPE firehose_counter_event_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"273ceeca-857e-46ff-87dd-3f4e7da552a6\"} 0";

        String output =
                "# HELP promhttp_metric_handler_requests_total Total number of scrapes by HTTP status code.\n" +
                        "# TYPE promhttp_metric_handler_requests_total counter\n" +
                        "promhttp_metric_handler_requests_total{code=\"200\"} 5434\n" +
                        "# HELP firehose_value_metric_uaa_requests_global_unhealthy_time Cloud Foundry Firehose 'requests.global.unhealthy.time' value metric from 'uaa'.\n" +
                        "# TYPE firehose_value_metric_uaa_requests_global_unhealthy_time gauge\n" +
                        "firehose_value_metric_uaa_requests_global_unhealthy_time{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"uaa\",product=\"Small Footprint VMware Tanzu Application Service\",source_id=\"uaa\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"gauge\"} 2144\n" +
                        "# HELP firehose_counter_event_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '273ceeca-857e-46ff-87dd-3f4e7da552a6'.\n" +
                        "# TYPE firehose_counter_event_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"273ceeca-857e-46ff-87dd-3f4e7da552a6\"} 0";
        assertEquals(output, metricCleaner.cleanMessage(input), "Complicated test failed");
    }

    @Test
    public void testCleanMessage3() {
        String input =
                "# HELP firehose_counter_event_26134_fa_3_0999_47_f_5_90_a_6_bbc_014_b_6_e_5_ab_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '26134fa3-0999-47f5-90a6-bbc014b6e5ab'.\n" +
                        "# TYPE firehose_counter_event_26134_fa_3_0999_47_f_5_90_a_6_bbc_014_b_6_e_5_ab_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_26134_fa_3_0999_47_f_5_90_a_6_bbc_014_b_6_e_5_ab_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"26134fa3-0999-47f5-90a6-bbc014b6e5ab\"} 0\n" +
                        "# HELP firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '273ceeca-857e-46ff-87dd-3f4e7da552a6'.\n" +
                        "# TYPE firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"273ceeca-857e-46ff-87dd-3f4e7da552a6\"} 0\n" +
                        "# HELP firehose_counter_event_8_e_4_ad_068_a_1_d_6_4777_b_68_d_60_fdec_186138_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '8e4ad068-a1d6-4777-b68d-60fdec186138'.\n" +
                        "# TYPE firehose_counter_event_8_e_4_ad_068_a_1_d_6_4777_b_68_d_60_fdec_186138_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_8_e_4_ad_068_a_1_d_6_4777_b_68_d_60_fdec_186138_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"8e4ad068-a1d6-4777-b68d-60fdec186138\"} 0\n" +
                        "# HELP firehose_counter_event_b_1_e_7_dfbd_64_e_5_493_c_b_1_b_8_36793_bd_4_daff_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from 'b1e7dfbd-64e5-493c-b1b8-36793bd4daff'.\n" +
                        "# TYPE firehose_counter_event_b_1_e_7_dfbd_64_e_5_493_c_b_1_b_8_36793_bd_4_daff_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_b_1_e_7_dfbd_64_e_5_493_c_b_1_b_8_36793_bd_4_daff_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"b1e7dfbd-64e5-493c-b1b8-36793bd4daff\"} 0";

        String output =
                "# HELP firehose_counter_event_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '26134fa3-0999-47f5-90a6-bbc014b6e5ab'.\n" +
                        "# TYPE firehose_counter_event_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"26134fa3-0999-47f5-90a6-bbc014b6e5ab\"} 0\n" +
                        "# HELP firehose_counter_event_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '273ceeca-857e-46ff-87dd-3f4e7da552a6'.\n" +
                        "# TYPE firehose_counter_event_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"273ceeca-857e-46ff-87dd-3f4e7da552a6\"} 0\n" +
                        "# HELP firehose_counter_event_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '8e4ad068-a1d6-4777-b68d-60fdec186138'.\n" +
                        "# TYPE firehose_counter_event_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"8e4ad068-a1d6-4777-b68d-60fdec186138\"} 0\n" +
                        "# HELP firehose_counter_event_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from 'b1e7dfbd-64e5-493c-b1b8-36793bd4daff'.\n" +
                        "# TYPE firehose_counter_event_tomcat_sessions_created_sessions_total_total counter\n" +
                        "firehose_counter_event_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"b1e7dfbd-64e5-493c-b1b8-36793bd4daff\"} 0";

        assertEquals(output, metricCleaner.cleanMessage(input), "Complicated test failed");
    }


    @Test
    public void testCleanMessageLine() {
        assertEquals("# HELP promhttp_metric_handler_requests_total Total number of scrapes by HTTP status code.",
                metricCleaner.cleanMessageLine("# HELP promhttp_metric_handler_requests_total Total number of scrapes by HTTP status code."),
                "Unchanged HELP comment"
        );
        assertEquals("# TYPE promhttp_metric_handler_requests_total counter",
                metricCleaner.cleanMessageLine("# TYPE promhttp_metric_handler_requests_total counter"),
                "Unchanged TYPE comment"
        );
        assertEquals("promhttp_metric_handler_requests_total{code=\"200\"} 5434",
                metricCleaner.cleanMessageLine("promhttp_metric_handler_requests_total{code=\"200\"} 5434"),
                "Unchanged metric"
        );

        assertEquals("# HELP firehose_value_metric_uaa_requests_global_unhealthy_time Cloud Foundry Firehose 'requests.global.unhealthy.time' value metric from 'uaa'.",
                metricCleaner.cleanMessageLine("# HELP firehose_value_metric_uaa_requests_global_unhealthy_time Cloud Foundry Firehose 'requests.global.unhealthy.time' value metric from 'uaa'."),
                "Unchanged HELP comment"
        );
        assertEquals("# TYPE firehose_value_metric_uaa_requests_global_unhealthy_time gauge",
                metricCleaner.cleanMessageLine("# TYPE firehose_value_metric_uaa_requests_global_unhealthy_time gauge"),
                "Unchanged TYPE comment"
        );
        assertEquals("firehose_value_metric_uaa_requests_global_unhealthy_time{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"uaa\",product=\"Small Footprint VMware Tanzu Application Service\",source_id=\"uaa\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"gauge\"} 2144",
                metricCleaner.cleanMessageLine("firehose_value_metric_uaa_requests_global_unhealthy_time{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"uaa\",product=\"Small Footprint VMware Tanzu Application Service\",source_id=\"uaa\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"gauge\"} 2144"),
                "Unchanged metric"
        );

        assertEquals("# HELP firehose_counter_event_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '273ceeca-857e-46ff-87dd-3f4e7da552a6'.",
                metricCleaner.cleanMessageLine("# HELP firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '273ceeca-857e-46ff-87dd-3f4e7da552a6'."),
                "Changed HELP comment"
        );
        assertEquals("# TYPE firehose_counter_event_tomcat_sessions_created_sessions_total_total counter",
                metricCleaner.cleanMessageLine("# TYPE firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total counter"),
                "Changed TYPE comment"
        );
        assertEquals("firehose_counter_event_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"273ceeca-857e-46ff-87dd-3f4e7da552a6\"} 0",
                metricCleaner.cleanMessageLine("firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"273ceeca-857e-46ff-87dd-3f4e7da552a6\"} 0"),
                "Changed metric"
        );
    }

    @Test
    public void testTypeMessageLine() {
        assertEquals("# TYPE promhttp_metric_handler_requests_total counter",
                metricCleaner.cleanMessageLine("# TYPE promhttp_metric_handler_requests_total counter"),
                "Unchanged TYPE comment"
        );
        assertEquals("# TYPE firehose_value_metric_uaa_requests_global_unhealthy_time gauge",
                metricCleaner.cleanMessageLine("# TYPE firehose_value_metric_uaa_requests_global_unhealthy_time gauge"),
                "Unchanged TYPE comment"
        );
        assertEquals("# TYPE firehose_counter_event_tomcat_sessions_created_sessions_total_total counter",
                metricCleaner.cleanMessageLine("# TYPE firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total counter"),
                "Changed TYPE comment"
        );
    }

    @Test
    public void testHelpMessageLine() {
        assertEquals("# HELP promhttp_metric_handler_requests_total Total number of scrapes by HTTP status code.",
                metricCleaner.cleanMessageLine("# HELP promhttp_metric_handler_requests_total Total number of scrapes by HTTP status code."),
                "Unchanged HELP comment"
        );
        assertEquals("# HELP firehose_value_metric_uaa_requests_global_unhealthy_time Cloud Foundry Firehose 'requests.global.unhealthy.time' value metric from 'uaa'.",
                metricCleaner.cleanMessageLine("# HELP firehose_value_metric_uaa_requests_global_unhealthy_time Cloud Foundry Firehose 'requests.global.unhealthy.time' value metric from 'uaa'."),
                "Unchanged HELP comment"
        );
        assertEquals("# HELP firehose_counter_event_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '273ceeca-857e-46ff-87dd-3f4e7da552a6'.",
                metricCleaner.cleanMessageLine("# HELP firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total Cloud Foundry Firehose 'tomcat_sessions_created_sessions_total' total counter event from '273ceeca-857e-46ff-87dd-3f4e7da552a6'."),
                "Changed HELP comment"
        );
    }

    @Test
    public void testMetricMessageLine() {
        assertEquals("promhttp_metric_handler_requests_total{code=\"200\"} 5434",
                metricCleaner.cleanMessageLine("promhttp_metric_handler_requests_total{code=\"200\"} 5434"),
                "Unchanged metric"
        );
        assertEquals("firehose_value_metric_uaa_requests_global_unhealthy_time{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"uaa\",product=\"Small Footprint VMware Tanzu Application Service\",source_id=\"uaa\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"gauge\"} 2144",
                metricCleaner.cleanMessageLine("firehose_value_metric_uaa_requests_global_unhealthy_time{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"uaa\",product=\"Small Footprint VMware Tanzu Application Service\",source_id=\"uaa\",system_domain=\"system.pcf.cloudnative.biz\",unit=\"gauge\"} 2144"),
                "Unchanged metric"
        );
        assertEquals("firehose_counter_event_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"273ceeca-857e-46ff-87dd-3f4e7da552a6\"} 0",
                metricCleaner.cleanMessageLine("firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total{bosh_deployment=\"cf-c71ea64d4c4ed791d7d3\",bosh_job_id=\"4c72e463-d17c-4f9d-8725-1e76ace82630\",bosh_job_ip=\"192.168.3.101\",bosh_job_name=\"control\",environment=\"CloudNative.biz\",origin=\"273ceeca-857e-46ff-87dd-3f4e7da552a6\"} 0"),
                "Changed metric"
        );
    }

    @Test
    public void testIsRemovableUUID() {
        assertEquals(false, metricCleaner.isRemovableGUID(null));
        assertEquals(false, metricCleaner.isRemovableGUID("b1e7dfbd64e5493cb1b836793bd4daffff"), "too long");
        assertEquals(false, metricCleaner.isRemovableGUID("b1e7dfbd64e5493cb1b836793bd4da"), "too short");
        assertEquals(true, metricCleaner.isRemovableGUID("26134fa3099947f590a6bbc014b6e5ab"));
        assertEquals(true, metricCleaner.isRemovableGUID("273ceeca857e46ff87dd3f4e7da552a6"));
        assertEquals(true, metricCleaner.isRemovableGUID("8e4ad068a1d64777b68d60fdec186138"));
        assertEquals(true, metricCleaner.isRemovableGUID("b1e7dfbd64e5493cb1b836793bd4daff"));

        assertEquals(false, metricCleaner.isRemovableGUID("26134fa3099947f590Fbbbc014b6e5ab"));
        assertEquals(false, metricCleaner.isRemovableGUID("273TESTa857e46ff87dd3f4e7da552a6"));
        assertEquals(false, metricCleaner.isRemovableGUID("8e4ad068a1dGHE77b68d60fdec186138"));
        assertEquals(false, metricCleaner.isRemovableGUID("b1e7dfbd64e5493cb1QQQQ793bd4daff"));
    }

    @Test
    public void testCleanFieldName() {
        assertEquals("firehose_counter_event_tomcat_sessions_created_sessions_total_total", metricCleaner.cleanFieldName("firehose_counter_event_tomcat_sessions_created_sessions_total_total"));
        assertEquals("firehose_counter_event_tomcat_sessions_created_sessions_total_total", metricCleaner.cleanFieldName("firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total"));
        assertEquals("firehose_counter_event_tomcat_sessions_created_sessions_total_total", metricCleaner.cleanFieldName("firehose_counter_event_26134_fa_3_0999_47_f_5_90_a_6_bbc_014_b_6_e_5_ab_tomcat_sessions_created_sessions_total_total"));
        assertEquals("firehose_counter_event_tomcat_sessions_created_sessions_total_total", metricCleaner.cleanFieldName("firehose_counter_event_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total"));
        assertEquals("firehose_counter_event_tomcat_sessions_created_sessions_total_total", metricCleaner.cleanFieldName("firehose_counter_event_8_e_4_ad_068_a_1_d_6_4777_b_68_d_60_fdec_186138_tomcat_sessions_created_sessions_total_total"));
        assertEquals("firehose_counter_event_tomcat_sessions_created_sessions_total_total", metricCleaner.cleanFieldName("firehose_counter_event_b_1_e_7_dfbd_64_e_5_493_c_b_1_b_8_36793_bd_4_daff_tomcat_sessions_created_sessions_total_total"));

        assertEquals("blabla_bbb", metricCleaner.cleanFieldName("blabla_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_bbb"));
        assertEquals("aaaa_bbb", metricCleaner.cleanFieldName("aaaa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_aa_bbb"));
        assertEquals("firehose_value_metric_tomcat_sessions_alive_max_seconds", metricCleaner.cleanFieldName("firehose_value_metric_b_1_e_7_dfbd_64_e_5_493_c_b_1_b_8_36793_bd_4_daff_tomcat_sessions_alive_max_seconds"));
    }

    @Test
    public void testTrimGUIDFromString() {
        assertEquals("_tomcat_sessions_created_sessions_total_total", metricCleaner.trimGUIDFromString("273ceeca857e46ff87dd3f4e7da552a6", "_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total"));
        assertEquals("_tomcat_sessions_created_sessions_total_total", metricCleaner.trimGUIDFromString("26134fa3099947f590a6bbc014b6e5ab", "_26134_fa_3_0999_47_f_5_90_a_6_bbc_014_b_6_e_5_ab_tomcat_sessions_created_sessions_total_total"));
        assertEquals("_tomcat_sessions_created_sessions_total_total", metricCleaner.trimGUIDFromString("273ceeca857e46ff87dd3f4e7da552a6", "_273_ceeca_857_e_46_ff_87_dd_3_f_4_e_7_da_552_a_6_tomcat_sessions_created_sessions_total_total"));
        assertEquals("_tomcat_sessions_created_sessions_total_total", metricCleaner.trimGUIDFromString("8e4ad068a1d64777b68d60fdec186138", "_8_e_4_ad_068_a_1_d_6_4777_b_68_d_60_fdec_186138_tomcat_sessions_created_sessions_total_total"));
        assertEquals("_tomcat_sessions_created_sessions_total_total", metricCleaner.trimGUIDFromString("b1e7dfbd64e5493cb1b836793bd4daff", "_b_1_e_7_dfbd_64_e_5_493_c_b_1_b_8_36793_bd_4_daff_tomcat_sessions_created_sessions_total_total"));
    }
}
