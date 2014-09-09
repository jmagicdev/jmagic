package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stuffy Doll")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("5")
@ColorIdentity({})
public final class StuffyDoll extends Card
{
	public static final class StuffyDollAbility0 extends StaticAbility
	{
		public StuffyDollAbility0(GameState state)
		{
			super(state, "As Stuffy Doll enters the battlefield, choose a player.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "As Stuffy Doll enters the battlefield, choose a player.");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator replacedMove = replacement.replacedByThis();

			EventFactory factory = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a player.");
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(replacedMove));
			factory.parameters.put(EventType.Parameter.CHOICE, Players.instance());
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.PLAYER, PlayerInterface.ChooseReason.CHOOSE_PLAYER));
			factory.setLink(this);
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();

			this.getLinkManager().addLinkClass(StuffyDollAbility2.class);
		}
	}

	public static final class StuffyDollAbility2 extends EventTriggeredAbility
	{
		public StuffyDollAbility2(GameState state)
		{
			super(state, "Whenever Stuffy Doll is dealt damage, it deals that much damage to the chosen player.");

			this.addPattern(whenIsDealtDamage(ABILITY_SOURCE_OF_THIS));

			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			SetGenerator thatMuch = Count.instance(triggerDamage);

			this.addEffect(permanentDealDamage(thatMuch, ChosenFor.instance(LinkedTo.instance(This.instance())), "It deals that much damage to the chosen player."));

			this.getLinkManager().addLinkClass(StuffyDollAbility0.class);
		}
	}

	public static final class StuffyDollAbility3 extends ActivatedAbility
	{
		public StuffyDollAbility3(GameState state)
		{
			super(state, "(T): Stuffy Doll deals 1 damage to itself.");
			this.costsTap = true;
			this.addEffect(permanentDealDamage(1, ABILITY_SOURCE_OF_THIS, "Stuffy Doll deals 1 damage to itself."));
		}
	}

	public StuffyDoll(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As Stuffy Doll enters the battlefield, choose a player.
		this.addAbility(new StuffyDollAbility0(state));

		// Whenever Stuffy Doll is dealt damage, it deals that much damage to
		// the chosen player.
		this.addAbility(new StuffyDollAbility2(state));

		// (T): Stuffy Doll deals 1 damage to itself.
		this.addAbility(new StuffyDollAbility3(state));
	}
}
