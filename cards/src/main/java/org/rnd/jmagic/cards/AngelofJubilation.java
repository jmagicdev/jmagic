package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Angel of Jubilation")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("1WWW")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AngelofJubilation extends Card
{
	public final static class CastOrActivateCostPattern implements EventPattern
	{
		private EventPattern pattern;

		public CastOrActivateCostPattern(EventPattern pattern)
		{
			this.pattern = pattern;
		}

		@Override
		public boolean match(Event event, Identified object, GameState state)
		{
			Event parent = event;

			while(parent.type != EventType.CAST_SPELL_OR_ACTIVATE_ABILITY)
			{
				if(parent.parent == null)
					return false;
				parent = parent.parent;
			}

			return this.pattern.match(event, object, state);
		}

		@Override
		public boolean looksBackInTime()
		{
			return this.pattern.looksBackInTime();
		}

		@Override
		public boolean matchesManaAbilities()
		{
			return this.pattern.looksBackInTime();
		}
	}

	public static final class AngelofJubilationAbility2 extends StaticAbility
	{
		public AngelofJubilationAbility2(GameState state)
		{
			super(state, "Players can't pay life or sacrifice creatures to cast spells or activate abilities.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);

			SimpleEventPattern payLife = new SimpleEventPattern(EventType.PAY_LIFE);

			SimpleEventPattern sacCritters = new SimpleEventPattern(EventType.SACRIFICE_ONE_PERMANENT);
			sacCritters.put(EventType.Parameter.PERMANENT, CreaturePermanents.instance());

			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(new CastOrActivateCostPattern(payLife), new CastOrActivateCostPattern(sacCritters)));
			this.addEffectPart(part);
		}
	}

	public AngelofJubilation(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Other nonblack creatures you control get +1/+1.
		SetGenerator otherNonBlack = RelativeComplement.instance(CREATURES_YOU_CONTROL, Union.instance(HasColor.instance(Color.BLACK), This.instance()));
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, otherNonBlack, "Other nonblack creatures you control", +1, +1, true));

		// Players can't pay life or sacrifice creatures to cast spells or
		// activate abilities.
		this.addAbility(new AngelofJubilationAbility2(state));
	}
}
