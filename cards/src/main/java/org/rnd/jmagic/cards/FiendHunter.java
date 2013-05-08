package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fiend Hunter")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class FiendHunter extends Card
{
	public static final class FiendHunterAbility0 extends EventTriggeredAbility
	{
		public FiendHunterAbility0(GameState state)
		{
			super(state, "When Fiend Hunter enters the battlefield, you may exile another target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.getLinkManager().addLinkClass(FiendHunterAbility1.class);

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS), "another target creature"));
			EventFactory exile = exile(target, "Exile another target creature");
			exile.setLink(this);
			this.addEffect(youMay(exile, "You may exile another target creature"));
		}
	}

	public static final class FiendHunterAbility1 extends EventTriggeredAbility
	{
		public FiendHunterAbility1(GameState state)
		{
			super(state, "When Fiend Hunter leaves the battlefield, return the exiled card to the battlefield under its owner's control.");
			this.addPattern(whenThisLeavesTheBattlefield());
			this.getLinkManager().addLinkClass(FiendHunterAbility0.class);

			SetGenerator exiledCard = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory ret = new EventFactory(EventType.MOVE_OBJECTS, "Return the exiled card to the battlefield under its owner's control.");
			ret.parameters.put(EventType.Parameter.CAUSE, This.instance());
			ret.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			ret.parameters.put(EventType.Parameter.CONTROLLER, OwnerOf.instance(exiledCard));
			ret.parameters.put(EventType.Parameter.OBJECT, exiledCard);
			this.addEffect(ret);
		}
	}

	public FiendHunter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// When Fiend Hunter enters the battlefield, you may exile another
		// target creature.
		this.addAbility(new FiendHunterAbility0(state));

		// When Fiend Hunter leaves the battlefield, return the exiled card to
		// the battlefield under its owner's control.
		this.addAbility(new FiendHunterAbility1(state));
	}
}
