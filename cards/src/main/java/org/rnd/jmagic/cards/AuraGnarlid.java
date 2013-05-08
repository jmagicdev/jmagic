package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aura Gnarlid")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class AuraGnarlid extends Card
{
	public static final class CantTouchThis extends StaticAbility
	{
		public CantTouchThis(GameState state)
		{
			super(state, "Creatures with power less than Aura Gnarlid's power can't block it.");

			SetGenerator relevantCreatures = HasPower.instance(Between.instance(Empty.instance(), Subtract.instance(PowerOf.instance(This.instance()), numberGenerator(1))));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(This.instance()), relevantCreatures)));
			this.addEffectPart(part);
		}
	}

	public static final class AuraPump extends StaticAbility
	{
		public AuraPump(GameState state)
		{
			super(state, "Aura Gnarlid gets +1/+1 for each Aura on the battlefield.");

			SetGenerator count = Count.instance(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.AURA)));

			this.addEffectPart(modifyPowerAndToughness(This.instance(), count, count));
		}
	}

	public AuraGnarlid(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Creatures with power less than Aura Gnarlid's power can't block it.
		this.addAbility(new CantTouchThis(state));

		// Aura Gnarlid gets +1/+1 for each Aura on the battlefield.
		this.addAbility(new AuraPump(state));
	}
}
