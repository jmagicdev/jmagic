package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Gaddock Teeg")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ADVISOR, SubType.KITHKIN})
@ManaCost("GW")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GaddockTeeg extends Card
{
	public static final class ProhibitWrath extends StaticAbility
	{
		public ProhibitWrath(GameState state)
		{
			super(state, "Noncreature spells with converted mana cost 4 or greater can't be cast.");

			MultipleSetPattern wrathyThings = new MultipleSetPattern(true);
			SetGenerator tooBig = HasConvertedManaCost.instance(Between.instance(4, null));
			wrathyThings.addPattern(SetPattern.CASTABLE);
			wrathyThings.addPattern(new SimpleSetPattern(RelativeComplement.instance(tooBig, HasType.instance(Type.CREATURE))));

			SimpleEventPattern castSomethingWrathy = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSomethingWrathy.put(EventType.Parameter.OBJECT, wrathyThings);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSomethingWrathy));
			this.addEffectPart(part);
		}
	}

	public static final class ProhibitFireball extends StaticAbility
	{
		public ProhibitFireball(GameState state)
		{
			super(state, "Noncreature spells with (X) in their mana costs can't be cast.");

			MultipleSetPattern fireballyThings = new MultipleSetPattern(true);
			fireballyThings.addPattern(SetPattern.CASTABLE);
			fireballyThings.addPattern(new SimpleSetPattern(RelativeComplement.instance(ManaCostContainsX.instance(), HasType.instance(Type.CREATURE))));

			SimpleEventPattern castSomethingFirebally = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSomethingFirebally.put(EventType.Parameter.OBJECT, fireballyThings);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSomethingFirebally));
			this.addEffectPart(part);
		}
	}

	public GaddockTeeg(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Noncreature spells with converted mana cost 4 or greater can't be
		// cast.
		this.addAbility(new ProhibitWrath(state));

		// Noncreature spells with {X} in their mana costs can't be cast.
		this.addAbility(new ProhibitFireball(state));
	}
}
