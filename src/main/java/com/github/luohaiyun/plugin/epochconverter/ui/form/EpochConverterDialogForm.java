package com.github.luohaiyun.plugin.epochconverter.ui.form;

import com.github.luohaiyun.plugin.epochconverter.util.SelectionMode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.TitlePanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class EpochConverterDialogForm extends DialogWrapper
{
	private JPanel mRootPanel;
	private JTextField mConvertField;
	private JButton mConvertButton;
	private JTextField mNowField;
	private JTextField mYearField;
	private JTextField mMonField;
	private JTextField mDayField;
	private JTextField mHourField;
	private JTextField mMinField;
	private JTextField mSecField;
	private JButton mReConvertButton;
	private ComboBox<SelectionMode> mReConvertComboBox;
	private TitlePanel mTitlePanel;
	private JTextField mConvertGMTTextField;
	private JTextField mConvertLocalTextField;
	private JTextField mConvertRelativeTextField;
	private JLabel mConvertAssumingLabel;
	private JPanel mConvertResultPanel;
	private JLabel mEpochTimestampLabel;
	private JTextField mReConvertEpochTimestampTextField;
	private JTextField mReConvertLocalTextField;
	private JTextField mReConvertGMTTextField;
	private JPanel mReConvertResultPanel;
	private JLabel mCurrentEpochTimeLabel;
	private JLabel mConvertEpochTipsLabel;
	private JLabel mConvertSupportTipsLabel;
	private JLabel mConvertAssumingTipsLabel;
	private JLabel mConvertGMTLabel;
	private JLabel mConvertLocalLabel;
	private JLabel mConvertRelativeLabel;
	private JLabel mReConvertYearLabel;
	private JLabel mReConvertMonLabel;
	private JLabel mReConvertDayLabel;
	private JLabel mReConvertHourLabel;
	private JLabel mReConvertMinLabel;
	private JLabel mReConvertSecLabel;
	private JLabel mReConvertGMTLabel;
	private JLabel mReConvertLocalLabel;

	protected EpochConverterDialogForm(@Nullable Project project)
	{
		super(project);
	}

	@NotNull
	protected final JLabel getEpochTimestampLabel()
	{
		return mEpochTimestampLabel;
	}

	@NotNull
	protected final JLabel getCurrentEpochTimeLabel()
	{
		return mCurrentEpochTimeLabel;
	}

	@NotNull
	protected final JLabel getConvertEpochTipsLabel()
	{
		return mConvertEpochTipsLabel;
	}

	@NotNull
	protected final JLabel getConvertSupportTipsLabel()
	{
		return mConvertSupportTipsLabel;
	}

	@NotNull
	protected final JLabel getConvertAssumingTipsLabel()
	{
		return mConvertAssumingTipsLabel;
	}

	@NotNull
	protected final JLabel getConvertGMTLabel()
	{
		return mConvertGMTLabel;
	}

	@NotNull
	protected final JLabel getConvertLocalLabel()
	{
		return mConvertLocalLabel;
	}

	@NotNull
	protected final JLabel getConvertRelativeLabel()
	{
		return mConvertRelativeLabel;
	}

	@NotNull
	protected final JLabel getReConvertYearLabel()
	{
		return mReConvertYearLabel;
	}

	@NotNull
	protected final JLabel getReConvertMonLabel()
	{
		return mReConvertMonLabel;
	}

	@NotNull
	protected final JLabel getReConvertDayLabel()
	{
		return mReConvertDayLabel;
	}

	@NotNull
	protected final JLabel getReConvertHourLabel()
	{
		return mReConvertHourLabel;
	}

	@NotNull
	protected final JLabel getReConvertMinLabel()
	{
		return mReConvertMinLabel;
	}

	@NotNull
	protected final JLabel getReConvertSecLabel()
	{
		return mReConvertSecLabel;
	}

	@NotNull
	protected final JLabel getReConvertGMTLabel()
	{
		return mReConvertGMTLabel;
	}

	@NotNull
	protected final JLabel getReConvertLocalLabel()
	{
		return mReConvertLocalLabel;
	}

	@NotNull
	protected final JPanel getReConvertResultPanel()
	{
		return mReConvertResultPanel;
	}

	@NotNull
	protected final JTextField getReConvertEpochTimestampTextField()
	{
		return mReConvertEpochTimestampTextField;
	}

	@NotNull
	protected final JTextField getReConvertLocalTextField()
	{
		return mReConvertLocalTextField;
	}

	@NotNull
	protected final JTextField getReConvertGMTTextField()
	{
		return mReConvertGMTTextField;
	}

	@NotNull
	protected final JTextField getConvertLocalTextField()
	{
		return mConvertLocalTextField;
	}

	@NotNull
	protected final JTextField getConvertGMTTextField()
	{
		return mConvertGMTTextField;
	}

	@NotNull
	protected final JTextField getConvertRelativeTextField()
	{
		return mConvertRelativeTextField;
	}

	@NotNull
	protected final JLabel getConvertAssumingLabel()
	{
		return mConvertAssumingLabel;
	}

	@NotNull
	protected final JPanel getConvertResultPanel()
	{
		return mConvertResultPanel;
	}

	@NotNull
	protected final JComponent getComponent()
	{
		return mRootPanel;
	}

	@NotNull
	protected final JTextField getNowField()
	{
		return mNowField;
	}

	@NotNull
	protected final JTextField getYearField()
	{
		return mYearField;
	}

	@NotNull
	protected final JTextField getMonField()
	{
		return mMonField;
	}

	@NotNull
	protected final JTextField getDayField()
	{
		return mDayField;
	}

	@NotNull
	protected final JTextField getSecField()
	{
		return mSecField;
	}

	@NotNull
	protected final JTextField getMinField()
	{
		return mMinField;
	}

	@NotNull
	protected final JTextField getHourField()
	{
		return mHourField;
	}

	@NotNull
	protected final JButton getReConvertButton()
	{
		return mReConvertButton;
	}

	@NotNull
	protected final TitlePanel getTitlePanel()
	{
		return mTitlePanel;
	}

	@NotNull
	protected final JTextField getConvertField()
	{
		return mConvertField;
	}

	@NotNull
	protected final JButton getConvertButton()
	{
		return mConvertButton;
	}

	@NotNull
	protected final ComboBox<SelectionMode> getReConvertComboBox()
	{
		return mReConvertComboBox;
	}


}
