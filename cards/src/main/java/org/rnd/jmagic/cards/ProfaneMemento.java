package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Profane Memento")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({})
public final class ProfaneMemento extends Card
{
	public static final class ProfaneMementoAbility0 extends EventTriggeredAbility
	{
		public ProfaneMementoAbility0(GameState state)
		{
			super(state, "Whenever a creature card is put into an opponent's graveyard from anywhere, you gain 1 life.");

			SetGenerator graveyard = GraveyardOf.instance(OpponentsOf.instance(You.instance()));
			SetGenerator creature = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance());
			this.addPattern(new SimpleZoneChangePattern(null, graveyard, creature, true));

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public ProfaneMemento(GameState state)
	{
		super(state);

		// Whenever a creature card is put into an opponent's graveyard from
		// anywhere, you gain 1 life.
		this.addAbility(new ProfaneMementoAbility0(state));
	}
}
