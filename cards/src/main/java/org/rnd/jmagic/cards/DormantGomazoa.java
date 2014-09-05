package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Dormant Gomazoa")
@Types({Type.CREATURE})
@SubTypes({SubType.JELLYFISH})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DormantGomazoa extends Card
{
	public static final class Undormant extends EventTriggeredAbility
	{
		public Undormant(GameState state)
		{
			super(state, "Whenever you become the target of a spell, you may untap Dormant Gomazoa.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_TARGET);
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			pattern.put(EventType.Parameter.TARGET, You.instance());
			this.addPattern(pattern);

			this.addEffect(youMay(untap(ABILITY_SOURCE_OF_THIS, "Untap Dormant Gomazoa."), "You may untap Dormant Gomazoa."));
		}
	}

	public DormantGomazoa(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Dormant Gomazoa enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, "Dormant Gomazoa"));

		// Dormant Gomazoa doesn't untap during your untap step.
		this.addAbility(new org.rnd.jmagic.abilities.DoesntUntapDuringYourUntapStep(state, "Dormant Gomazoa"));

		// Whenever you become the target of a spell, you may untap Dormant
		// Gomazoa.
		this.addAbility(new Undormant(state));
	}
}
