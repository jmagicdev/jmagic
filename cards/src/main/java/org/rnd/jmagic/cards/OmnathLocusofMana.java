package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Omnath, Locus of Mana")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = FromTheVaultLegends.class, r = Rarity.MYTHIC), @Printings.Printed(ex = Worldwake.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class OmnathLocusofMana extends Card
{
	public static final class GreenDoesntEmpty extends StaticAbility
	{
		public GreenDoesntEmpty(GameState state)
		{
			super(state, "Green mana doesn't empty from your mana pool as steps and phases end.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_DOESNT_EMPTY);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(new ManaColorPattern(Color.GREEN)));
			this.addEffectPart(part);
		}
	}

	private static class YourGreenMana extends SetGenerator
	{
		private static SetGenerator _green = Identity.instance(Color.GREEN);
		private static SetGenerator _instance = new YourGreenMana();

		public static SetGenerator instance()
		{
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Player you = ((GameObject)thisObject).getController(state);
			Color green = _green.evaluate(state, thisObject).getOne(Color.class);
			Set ret = new Set();
			for(ManaSymbol m: you.pool)
				if(m.colors.contains(green))
					ret.add(m);
			return ret;
		}
	}

	public static final class ManaPump extends StaticAbility
	{
		public ManaPump(GameState state)
		{
			super(state, "Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool.");

			SetGenerator amount = Count.instance(YourGreenMana.instance());
			this.addEffectPart(modifyPowerAndToughness(This.instance(), amount, amount));
		}
	}

	public OmnathLocusofMana(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Green mana doesn't empty from your mana pool as steps and phases end.
		this.addAbility(new GreenDoesntEmpty(state));

		// Omnath, Locus of Mana gets +1/+1 for each green mana in your mana
		// pool.
		this.addAbility(new ManaPump(state));
	}
}
