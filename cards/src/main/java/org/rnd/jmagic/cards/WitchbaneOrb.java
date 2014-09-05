package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Witchbane Orb")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class WitchbaneOrb extends Card
{
	public static final class WitchbaneOrbAbility0 extends EventTriggeredAbility
	{
		public WitchbaneOrbAbility0(GameState state)
		{
			super(state, "When Witchbane Orb enters the battlefield, destroy all Curses attached to you.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(destroy(Intersect.instance(HasSubType.instance(SubType.CURSE), AttachedTo.instance(You.instance())), "Destroy all Curses attached to you."));
		}
	}

	public static final class WitchbaneOrbAbility1 extends StaticAbility
	{
		public WitchbaneOrbAbility1(GameState state)
		{
			super(state, "You have hexproof.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_PLAYER);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Hexproof.class)));
			this.addEffectPart(part);
		}
	}

	public WitchbaneOrb(GameState state)
	{
		super(state);

		// When Witchbane Orb enters the battlefield, destroy all Curses
		// attached to you.
		this.addAbility(new WitchbaneOrbAbility0(state));

		// You have hexproof. (You can't be the target of spells or abilities
		// your opponents control, including Aura spells.)
		this.addAbility(new WitchbaneOrbAbility1(state));
	}
}
