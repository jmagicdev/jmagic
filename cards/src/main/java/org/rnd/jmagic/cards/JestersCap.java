package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Jester's Cap")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = FromTheVaultRelics.class, r = Rarity.MYTHIC), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = IceAge.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class JestersCap extends Card
{
	public static final class JestersCapAbility0 extends ActivatedAbility
	{
		public JestersCapAbility0(GameState state)
		{
			super(state, "(2), (T), Sacrifice Jester's Cap: Search target player's library for three cards and exile them. Then that player shuffles his or her library.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Jester's Cap"));

			Target target = this.addTarget(Players.instance(), "target player");

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search target player's library for up to three cards and exile them. Then that player shuffles his or her library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.TARGET, targetedBy(target));
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			search.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			this.addEffect(search);
		}
	}

	public JestersCap(GameState state)
	{
		super(state);

		// (2), (T), Sacrifice Jester's Cap: Search target player's library for
		// three cards and exile them. Then that player shuffles his or her
		// library.
		this.addAbility(new JestersCapAbility0(state));
	}
}
