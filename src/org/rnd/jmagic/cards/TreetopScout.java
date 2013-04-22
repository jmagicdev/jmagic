package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Treetop Scout")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TreetopScout extends Card
{
	public static final class Evasion extends StaticAbility
	{
		public Evasion(GameState state)
		{
			super(state, "Treetop Scout can't be blocked except by creatures with flying.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(RelativeComplement.instance(Blocking.instance(This.instance()), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class))));
			this.addEffectPart(part);
		}
	}

	public TreetopScout(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Treetop Scout can't be blocked except by creatures with flying.
		this.addAbility(new Evasion(state));
	}
}
