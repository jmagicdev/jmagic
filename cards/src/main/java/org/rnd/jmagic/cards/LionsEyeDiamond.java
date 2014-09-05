package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lion's Eye Diamond")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Mirage.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class LionsEyeDiamond extends Card
{
	public static final class LionsEyeDiamondAbility0 extends ActivatedAbility
	{
		public LionsEyeDiamondAbility0(GameState state)
		{
			super(state, "Sacrifice Lion's Eye Diamond, Discard your hand: Add three mana of any one color to your mana pool. Activate this ability only any time you could cast an instant.");
			this.addCost(sacrificeThis("Lion's Eye Diamond"));
			// Discard your hand
			this.addCost(discardHand(You.instance(), "Discard your hand"));

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add three mana of any one color to your mana pool.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(WUBRG)")));
			addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			addMana.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(addMana);

			this.addActivateRestriction(Not.instance(Intersect.instance(You.instance(), PlayerWithPriority.instance())));
		}
	}

	public LionsEyeDiamond(GameState state)
	{
		super(state);

		// Sacrifice Lion's Eye Diamond, Discard your hand: Add three mana of
		// any one color to your mana pool. Activate this ability only any time
		// you could cast an instant.
		this.addAbility(new LionsEyeDiamondAbility0(state));
	}
}
