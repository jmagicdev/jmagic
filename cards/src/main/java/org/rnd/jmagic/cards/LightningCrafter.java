package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lightning Crafter")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SHAMAN})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Morningtide.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class LightningCrafter extends Card
{
	public static final class ChampionAGoblinOrShaman extends org.rnd.jmagic.abilities.keywords.Champion
	{
		public ChampionAGoblinOrShaman(GameState state)
		{
			super(state, "Champion a Goblin or Shaman");
		}

		@Override
		protected java.util.List<NonStaticAbility> createNonStaticAbilities()
		{
			java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

			ret.add(new ExileAGoblinOrShaman(this.state));
			ret.add(new ReturnAGoblinOrShaman(this.state));

			return ret;
		}

		public static final class ExileAGoblinOrShaman extends ChampionExileAbility
		{
			public ExileAGoblinOrShaman(GameState state)
			{
				super(state, "Goblin or Shaman", Union.instance(HasSubType.instance(SubType.GOBLIN), HasSubType.instance(SubType.SHAMAN)), ReturnAGoblinOrShaman.class);
			}
		}

		public static final class ReturnAGoblinOrShaman extends ChampionReturnAbility
		{
			public ReturnAGoblinOrShaman(GameState state)
			{
				super(state, ExileAGoblinOrShaman.class);
			}
		}
	}

	public static final class TapForThreeDamage extends ActivatedAbility
	{
		public TapForThreeDamage(GameState state)
		{
			super(state, "(T): Lightning Crafter deals 3 damage to target creature or player.");

			this.costsTap = true;

			Target target = this.addTarget(Union.instance(Players.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(Battlefield.instance()))), "target creature or player");
			this.addEffect(permanentDealDamage(3, targetedBy(target), "Lightning Crafter deals 3 damage to target creature or player."));
		}
	}

	public LightningCrafter(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new ChampionAGoblinOrShaman(state));
		this.addAbility(new TapForThreeDamage(state));
	}
}
