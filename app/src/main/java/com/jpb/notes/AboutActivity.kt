package com.jpb.notes

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.*
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jpb.notes.oss.licenses.ui.OSSLicense
import java.util.*

class AboutActivity : AbsAboutActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.about_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.rate) {
            MaterialAlertDialogBuilder(this@AboutActivity) // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                .setIcon(R.drawable.ic_round_star_rate_24)
                .setTitle(getString(R.string.rate_notes))
                .setView(R.layout.ratelayout)
                .setPositiveButton(
                    getString(R.string.yes), null
                )
                .setNegativeButton(getString(R.string.notext), null)
                .show()
            return true
        }
        if (item.itemId == R.id.licence) {
            if (Locale.getDefault().getLanguage() != "en")
            {
                MaterialAlertDialogBuilder(this@AboutActivity) // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                    .setIcon(R.drawable.ic_round_warning_24)
                    .setTitle(getString(R.string.osswarning))
                    .setMessage(getString(R.string.licenceonlyenglish))
                    .setPositiveButton(
                        getString(R.string.yesr)
                    ) { dialog, which ->
                        MaterialAlertDialogBuilder(this@AboutActivity) // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                            .setTitle("Choose OSS License UI")
                            .setPositiveButton(
                                "jpb Custom UI"
                            ) { dialog, which ->

                                val intent = Intent(applicationContext, OSSLicense::class.java)
                                startActivity(intent)
                            }
                            .setNegativeButton( "GMS UI", { dialog, which ->

                                val intent = Intent(applicationContext, OssLicensesMenuActivity::class.java)
                                startActivity(intent)
                            } )
                            .show()
                    }
                    .setNegativeButton(getString(R.string.notext)) { dialog, which ->

                    }
                    .show()
            } else {
                MaterialAlertDialogBuilder(this@AboutActivity) // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                    .setTitle("Choose OSS License UI")
                    .setPositiveButton(
                        "jpb Custom UI"
                    ) { dialog, which ->

                        val intent =
                            Intent(applicationContext, OSSLicense::class.java)
                        startActivity(intent)
                    }
                    .setNegativeButton("GMS UI") { dialog, which ->

                        val intent =
                            Intent(applicationContext, OssLicensesMenuActivity::class.java)
                        startActivity(intent)
                    }
                    .show()
            }
            return true
        }
        return false
    }
    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher)
        slogan.text = getString(R.string.notetitle)
        version.text = getString(R.string.versionletter) + BuildConfig.VERSION_NAME
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.add(Category(getString(R.string.aboutapp)))
        items.add(Card(getString(R.string.card_content)))
        items.add(Category(getString(R.string.devs)))
        items.add(
            Contributor(
                R.drawable.jpb,
                "jpb",
                getString(R.string.devdesc),
                "https://occoam.com/jpb"
            )
        )
        items.add(Category(getString(R.string.oss_license_title)))
        items.add(
            License(
                "MultiType",
                "drakeet",
                License.APACHE_2,
                "https://github.com/drakeet/MultiType"
            )
        )
        items.add(
            License(
                "about-page",
                "drakeet",
                License.APACHE_2,
                "https://github.com/drakeet/about-page"
            )
        )
        if (Locale.getDefault().getLanguage() == "en")
        {
            items.add(Category("Changelog"))
            items.add(Card("•   Updates AndroidX Core library to 1.8.0 alpha 4"))
            items.add(Category("Licence"))
            items.add(Card("Copyright 2021-2022 jpb\n" +
                    "\n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    "you may not use this file except in compliance with the License.\n" +
                    "You may obtain a copy of the License at\n" +
                    "\n" +
                    "    http://www.apache.org/licenses/LICENSE-2.0\n" +
                    "\n" +
                    "Unless required by applicable law or agreed to in writing, software\n" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "See the License for the specific language governing permissions and\n" +
                    "limitations under the License."))
        }

    }
}