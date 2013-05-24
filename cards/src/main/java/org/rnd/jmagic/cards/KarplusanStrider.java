package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Karplusan Strider")
@Types({Type.CREATURE})
@SubTypes({SubType.YETI})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class KarplusanStrider extends Card
{
	// Karplusan Strider can't be the target of blue or black spells.
	public static final class UntargetableByBU extends StaticAbility
	{
		public UntargetableByBU(GameState state)
		{
			super(state, "Karplusan Strider can't be the target of blue or black spells.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_THE_TARGET_OF);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			SetGenerator blueOrBlackSpells = Intersect.instance(Spells.instance(), HasColor.instance(Color.BLUE, Color.BLACK));
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(new SimpleSetPattern(blueOrBlackSpells)));
			this.addEffectPart(part);
		}
	}

	public KarplusanStrider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		this.addAbility(new UntargetableByBU(state));
	}
}
