package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Brutalizer Exarch")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("5G")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BrutalizerExarch extends Card
{
	public static final class BrutalizerExarchAbility0 extends EventTriggeredAbility
	{
		public BrutalizerExarchAbility0(GameState state)
		{
			super(state, "When Brutalizer Exarch enters the battlefield, choose one \u2014\n\u2022 Search your library for a creature card, reveal it, then shuffle your library and put that card on top of it.\n\u2022 Put target noncreature permanent on the bottom of its owner's library.");
			this.addPattern(whenThisEntersTheBattlefield());

			// Search your library for a creature card, reveal it, then shuffle
			// your library and put that card on top of it
			{
				EventFactory effect = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_ON_TOP, "Search your library for a creature card, reveal it, then shuffle your library and put that card on top of it.");
				effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
				effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
				effect.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.CREATURE)));
				this.addEffect(1, effect);
			}

			// put target noncreature permanent on the bottom of its owner's
			// library
			{
				SetGenerator target = targetedBy(this.addTarget(2, RelativeComplement.instance(Permanents.instance(), CreaturePermanents.instance()), "target noncreature permanent"));

				EventFactory effect = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target noncreature permanent on the bottom of its owner's library.");
				effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
				effect.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
				effect.parameters.put(EventType.Parameter.OBJECT, target);
				this.addEffect(2, effect);
			}
		}
	}

	public BrutalizerExarch(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Brutalizer Exarch enters the battlefield, choose one \u2014
		// Search your library for a creature card, reveal it, then shuffle your
		// library and put that card on top of it; or put target noncreature
		// permanent on the bottom of its owner's library.
		this.addAbility(new BrutalizerExarchAbility0(state));
	}
}
