package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Spellbreaker Behemoth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("1RGG")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class SpellbreakerBehemoth extends Card
{
	public static final class BreakSpells extends StaticAbility
	{
		public BreakSpells(GameState state)
		{
			super(state, "Creature spells you control with power 5 or greater can't be countered.");

			SetGenerator yourGuys = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance(), Stack.instance()));
			SetGenerator yourBigGuys = Intersect.instance(yourGuys, HasPower.instance(Between.instance(5, null)));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.COUNTER);
			pattern.put(EventType.Parameter.OBJECT, yourBigGuys);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));

			this.addEffectPart(part);
		}
	}

	public SpellbreakerBehemoth(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Spellbreaker Behemoth can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Creature spells you control with power 5 or greater can't be
		// countered.
		this.addAbility(new BreakSpells(state));
	}
}
