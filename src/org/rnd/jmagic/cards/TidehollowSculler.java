package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tidehollow Sculler")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.ZOMBIE})
@ManaCost("WB")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class TidehollowSculler extends Card
{
	public static final class TidehollowScullerAbility0 extends EventTriggeredAbility
	{
		public TidehollowScullerAbility0(GameState state)
		{
			super(state, "When Tidehollow Sculler enters the battlefield, target opponent reveals his or her hand and you choose a nonland card from it. Exile that card.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			SetGenerator inTargetsHand = InZone.instance(HandOf.instance(target));

			EventFactory reveal = new EventFactory(EventType.REVEAL, "Target opponent reveals his or her hand");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, inTargetsHand);
			this.addEffect(reveal);

			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "and you choose a nonland card from it. Exile that card.");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(inTargetsHand, HasType.instance(Type.LAND)));
			exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(TidehollowScullerAbility1.class);
		}
	}

	public static final class TidehollowScullerAbility1 extends EventTriggeredAbility
	{
		public TidehollowScullerAbility1(GameState state)
		{
			super(state, "When Tidehollow Sculler leaves the battlefield, return the exiled card to its owner's hand.");
			this.addPattern(whenThisLeavesTheBattlefield());

			this.getLinkManager().addLinkClass(TidehollowScullerAbility0.class);
			SetGenerator exiledCard = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return the exiled card to its owner's hand.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(exiledCard)));
			move.parameters.put(EventType.Parameter.OBJECT, exiledCard);
			this.addEffect(move);
		}
	}

	public TidehollowSculler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Tidehollow Sculler enters the battlefield, target opponent
		// reveals his or her hand and you choose a nonland card from it. Exile
		// that card.
		this.addAbility(new TidehollowScullerAbility0(state));

		// When Tidehollow Sculler leaves the battlefield, return the exiled
		// card to its owner's hand.
		this.addAbility(new TidehollowScullerAbility1(state));
	}
}
