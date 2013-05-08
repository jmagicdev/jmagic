package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Havengul Lich")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ZOMBIE})
@ManaCost("3UB")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class HavengulLich extends Card
{
	public static final class HavengulLichAbility0 extends ActivatedAbility
	{
		public HavengulLichAbility0(GameState state)
		{
			super(state, "(1): You may cast target creature card in a graveyard this turn. When you cast that card this turn, Havengul Lich gains all activated abilities of that card until end of turn.");
			this.setManaCost(new ManaPool("(1)"));

			SetGenerator legal = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance())));
			SetGenerator target = targetedBy(this.addTarget(legal, "target creature card in a graveyard"));

			ContinuousEffect.Part mayCast = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			mayCast.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			mayCast.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffect(createFloatingEffect("You may cast target creature card in a graveyard this turn.", mayCast));

			SimpleEventPattern cast = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			cast.put(EventType.Parameter.PLAYER, You.instance());
			cast.put(EventType.Parameter.OBJECT, FutureSelf.instance(delayedTriggerContext(target)));

			SetGenerator thatCard = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);

			ContinuousEffect.Part gainAbilities = new ContinuousEffect.Part(ContinuousEffectType.COPY_ABILITIES_TO_OBJECT);
			gainAbilities.parameters.put(ContinuousEffectType.Parameter.OBJECT, AbilitySource.instance(This.instance()));
			gainAbilities.parameters.put(ContinuousEffectType.Parameter.ABILITY, ActivatedAbilitiesOf.instance(thatCard));
			EventFactory getCrazy = createFloatingEffect("Havengul Lich gains all activated abilities of that card until end of turn", gainAbilities);

			EventFactory getCrazyLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "When you cast that card this turn, Havengul Lich gains all activated abilities of that card until end of turn.");
			getCrazyLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			getCrazyLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(cast));
			getCrazyLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(getCrazy));
			this.addEffect(getCrazyLater);
		}
	}

	public HavengulLich(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// (1): You may cast target creature card in a graveyard this turn. When
		// you cast that card this turn, Havengul Lich gains all activated
		// abilities of that card until end of turn.
		this.addAbility(new HavengulLichAbility0(state));
	}
}
