package com.jacksonplayz.thinkingwithaperture.blocks;

import com.jacksonplayz.thinkingwithaperture.ThinkingWithAperture;
import com.jacksonplayz.thinkingwithaperture.client.gui.ThinkingWithApertureGuiHandler;
import com.jacksonplayz.thinkingwithaperture.tileentity.TileEntityTestChamberSign;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTestChamberSign extends BlockBase
{
    public static final AxisAlignedBB[] BOXES = { new AxisAlignedBB(0, 0, 0, 1, 2, 0.0625), new AxisAlignedBB(15 * 0.0625, 0, 0, 1, 2, 1), new AxisAlignedBB(0, 0, 15 * 0.0625, 1, 2, 1), new AxisAlignedBB(0, 0, 0, 0.0625, 2, 1), new AxisAlignedBB(0, -1, 0, 1, 1, 0.0625), new AxisAlignedBB(15 * 0.0625, -1, 0, 1, 1, 1), new AxisAlignedBB(0, -1, 15 * 0.0625, 1, 1, 1), new AxisAlignedBB(0, -1, 0, 0.0625, 1, 1) };
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool TOP = PropertyBool.create("top");;

    public BlockTestChamberSign(String name)
    {
        super(Material.GLASS, name);
        this.setLightLevel(2.0F);
        this.setSoundType(SoundType.GLASS);
        this.setCreativeTab(ThinkingWithAperture.TAB);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TOP, false));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        player.openGui(ThinkingWithAperture.instance, ThinkingWithApertureGuiHandler.TEST_CHAMBER_SIGN, world, pos.getX(), state.getValue(TOP) ? pos.getY() - 1 : pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return BOXES[state.getValue(FACING).getHorizontalIndex() + (state.getValue(TOP) ? 4 : 0)];
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(TOP) ? 4 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(TOP, meta / 4 > 0);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        world.setBlockToAir(state.getValue(TOP) ? pos.down() : pos.up());
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing.getAxis() != EnumFacing.Axis.Y ? facing : placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        world.setBlockState(pos.up(), this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(TOP, true));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return !state.getValue(TOP);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityTestChamberSign();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TOP);
    }
}
