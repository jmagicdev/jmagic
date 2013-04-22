package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spine of Ish Sah")
@Types({Type.ARTIFACT})
@ManaCost("7")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({})
public final class SpineofIshSah extends Card
{
	public static final class SpineofIshSahAbility0 extends EventTriggeredAbility
	{
		public SpineofIshSahAbility0(GameState state)
		{
			super(state, "When Spine of Ish Sah enters the battlefield, destroy target permanent.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
			this.addEffect(destroy(target, "Destroy target permanent."));
		}
	}

	public static final class SpineofIshSahAbility1 extends EventTriggeredAbility
	{
		public SpineofIshSahAbility1(GameState state)
		{
			super(state, "When Spine of Ish Sah is put into a graveyard from the battlefield, return Spine of Ish Sah to its owner's hand.");
			this.addPattern(whenThisDies());

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return Spine of Ish Sah to its owner's hand.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			move.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(move);
		}
	}

	public SpineofIshSah(GameState state)
	{
		super(state);

		// When Spine of Ish Sah enters the battlefield, destroy target
		// permanent.
		this.addAbility(new SpineofIshSahAbility0(state));

		// When Spine of Ish Sah is put into a graveyard from the battlefield,
		// return Spine of Ish Sah to its owner's hand.
		this.addAbility(new SpineofIshSahAbility1(state));
	}
}
