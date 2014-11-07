package com.gannon.settingsdemo;

import android.annotation.TargetApi;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

/**
 * Setings Activity
 */
public class Settings extends PreferenceActivity
{
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener preferenceListener;

    /**
     * Static Initializer
     */
    static
    {
        preferenceListener = new Preference.OnPreferenceChangeListener()
        {
            /**
             * Change preference summary to its value as string
             * @param preference Preference changed
             * @param value Object value updated
             * @return True
             */
            @Override
            public boolean onPreferenceChange(Preference preference, Object value)
            {
                String stringValue = value.toString();

                if (preference instanceof ListPreference)
                {
                    // For list preferences, look up the correct display value in
                    // the preference's 'entries' list.
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            index >= 0
                                    ? listPreference.getEntries()[index]
                                    : null);

                }
                else if (preference instanceof RingtonePreference)
                {
                    // For ringtone preferences, look up the correct display value
                    // using RingtoneManager.
                    if (TextUtils.isEmpty(stringValue))
                    {
                        // Empty values correspond to 'silent' (no ringtone).
                        preference.setSummary(R.string.pref_ringtone_silent);

                    }
                    else
                    {
                        Ringtone ringtone = RingtoneManager.getRingtone(
                                preference.getContext(), Uri.parse(stringValue));

                        if (ringtone == null)
                        {
                            // Clear the summary if there was a lookup error.
                            preference.setSummary(null);
                        }
                        else
                        {
                            // Set the summary to reflect the new ringtone display
                            // name.
                            String name = ringtone.getTitle(preference.getContext());
                            preference.setSummary(name);
                        }
                    }

                }
                else
                {
                    // For all other preferences, set the summary to the value's
                    // simple string representation.
                    preference.setSummary(stringValue);
                }
                return true;
            }
        };
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #preferenceListener
     */
    private static void bindSummaryToCurrentValue(Preference preference)
    {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(preferenceListener);

        // Trigger the listener immediately with the preference's
        // current value.
        preferenceListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * Options item selection
     * @param item MenuItem item selected
     * @return True
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            // Revert preference selection on Action Bar back button
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Load headers for main fragments into preference activity
     * @param headers List of Headers from pref_headers xml
     */
    @Override
    public void onBuildHeaders(List<Header> headers)
    {
        super.onBuildHeaders(headers);

        loadHeadersFromResource(R.xml.pref_headers, headers);
    }

    /**
     * Determine if fragment selected is valid
     * @param fragmentName String name of fragment launching
     * @return True
     */
    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return true; // change as needed
    }

    /**
     * This fragment shows general preferences
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class General extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_general);

            bindSummaryToCurrentValue(findPreference("example_text"));
            bindSummaryToCurrentValue(findPreference("example_list"));
        }
    }

    /**
     * This fragment shows notification preferences
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class Notification extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);

            bindSummaryToCurrentValue(findPreference("notifications_new_message_ringtone"));
        }
    }

    /**
     * This fragment shows data and sync preferences
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DataSync extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_data_sync);

            bindSummaryToCurrentValue(findPreference("sync_frequency"));
        }
    }
}
